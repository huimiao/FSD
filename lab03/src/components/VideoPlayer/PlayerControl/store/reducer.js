import { fromJS } from "immutable";
import * as constants from "./constants";

const defaultState = fromJS({
  speakerStatus: "on",
  likeNum: 0,
  disLikeNum: 0
});

export default (state = defaultState, action) => {
  switch (action.type) {
    case constants.TOGGLE_SPEAKER:
      return state.set("speakerStatus", action.speakerStatus);
    case constants.UPDATE_SCORE:
      return state.set("likeNum", action.likeNum).set("disLikeNum", action.disLikeNum);
    default:
      return state;
  }
};
