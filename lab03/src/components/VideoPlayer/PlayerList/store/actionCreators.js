import axios from "axios";
import { fromJS } from "immutable";
import * as constants from "./constants";

const setPlayList = result => ({
  type: constants.INIT_PLAY_LIST_DATA,
  playItems: fromJS(result)
});

export const initPlayList = () => {
  return dispatch => {
    axios.get("http://localhost:3500/youtube").then(res => {
      const result = res.data;
      dispatch(setPlayList(result));
    });
  };
};

export const saveNewPlayItem = playItem => {
  return dispatch => {
    axios.post("http://localhost:3500/youtube", playItem).then(res => {
      dispatch({
        type: constants.ADD_NEW_PLAYITEM,
        newItem: playItem
      });
    }); 
  };
}

export const deletePlayItem = id => {
  return dispatch => {
    axios.delete("http://localhost:3500/youtube/"+ id).then(res => {
      dispatch({
        type: constants.DELETE_PLAYITEM,
        id
      });
    }); 
  };
}

export const updatePlayItemStatus = (id, status) => {
  return dispatch => {
    axios.patch("http://localhost:3500/youtube/"+ id, {approved: status}).then(res => {
      dispatch({
        type: constants.UPDATE_PLAYITEM_STATUS,
        id,
        status
      });
    }); 
  };
}

export const updatePlayItem = (item) => {
  return dispatch => {
    axios.patch("http://localhost:3500/youtube/"+ item.id, {title: item.title, url: item.url, approved: 0}).then(res => {
      dispatch({
        type: constants.UPDATE_PLAYITEM,
        ...item
      });
    }); 
  };
}

export const playItemSelected = id => dispatch => {
  dispatch({
    type: constants.ITEM_SELECTED,
    id
  });
};

export const changeVideoPlayingStatus = operation => ({
  type: constants.CHANGE_VIDEO_PLAYER_STATUS,
  status: operation
});

export const resetAction = () => {
  return dispatch => {
    dispatch({
      type: constants.RESET,
      status: "paused"
    });
  };
};

export const closeNew = () => {
  return dispatch => dispatch({ type: constants.CLOSE_NEW, showNew: false });
};

export const showNew = () => {
  return dispatch => dispatch({ type: constants.OPEN_NEW, showNew: true });
};

export const closeEditAction = () => {
  return dispatch => dispatch({ type: constants.CLOSE_EDIT, showEdit: false, showNew: true });
};

export const showEditAction = (item) => {
  return dispatch => {
    dispatch({ type: constants.OPEN_EDIT, showEdit: true, showNew: false, editTitle: item.get("title"), editUrl: item.get("url")});
  };
};

export const closeDeleteConfirmAction = () => {
  return dispatch => dispatch({ type: constants.DELETE_CONFIRM_CLOSE });
};

export const showDeleteConfirmAction = () => {
  return dispatch => {
    dispatch({ type: constants.DELETE_CONFIRM_NEW });
  };
};

export const inputValueUpdated = item => {
  return dispatch => {
    dispatch({ type: constants.INPUT_VALUE_UPDATED, ...item});
  };
}

export const setValidated = form => ({
  type: constants.FORM_VALIDATED,
  ...form
})
