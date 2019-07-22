import { fromJS } from "immutable";
import * as constants from "./constants";

const defaultState = fromJS({
  selectedItem: null,
  status: "paused",
  playItems: fromJS([
    {
      id: "U2FkaGd1cnUgYW5kIFBoeXNpY3M=",
      title: "Sadhguru and Physics",
      url: "http://127.0.0.1:3500/1.mp4",
      status: "added",
      approved: 1,
      likes: 22,
      unlikes: 4,
      currentStatus: "playing",
      exitplayprogress: 30
    }
  ]),
  showNew: false,
  showEdit: false,
  showDeleteConfirm: false,
  newTitle: "",
  newUrl: "",
  editTitle:"",
  editUrl:"",
  editFormValidate: false,  
  newFormValidate: false
});

export default (state = defaultState, action) => {   
  let newList = [];  
  let n = {
    playItems: state.get("playItems")
  };

  switch (action.type) {
    case constants.INIT_PLAY_LIST_DATA:
      return state.set("playItems", action.playItems);
    case constants.ITEM_SELECTED:
      let item = state.get("playItems").filter( i => {
        return i.get("id") === action.id;
      });
      return state.set("selectedItem", item.get(0)).set("status", "stopped");
    case constants.CHANGE_VIDEO_PLAYER_STATUS:
      return state.set("status", action.status);    
    case constants.CLOSE_NEW:
      return state.set("showNew", action.showNew);   
    case constants.OPEN_NEW:
      return state.set("showNew", action.showNew);  
    case constants.CLOSE_EDIT:
      return state.set("showNew", true).set("showEdit", false);   
    case constants.OPEN_EDIT:
      return state.set("showNew", false).set("showEdit", true)
      .set("editTitle", action.editTitle).set("editUrl", action.editUrl);
    case constants.DELETE_CONFIRM_CLOSE:
      return state.set("showDeleteConfirm", false).set("showNew", true);   
    case constants.DELETE_CONFIRM_NEW:
      return state.set("showDeleteConfirm", true).set("showNew", false);
    case constants.INPUT_VALUE_UPDATED:
      for(let key in action){
        if(key === "type")
          continue;
        return state.set(key, action[key]);
      }
      break;
    case constants.FORM_VALIDATED:      
      for(let key in action){
        if(key === "type")
          continue;
        return state.set(key, action[key]);
      }
      break;   
    case constants.ADD_NEW_PLAYITEM:     
    let nn = n.playItems.push(fromJS({...action.newItem}));   
      return state.set("playItems", fromJS(nn))
      .set("newTitle","").set("newUrl", "").set("newFormValidate",false);
    case constants.DELETE_PLAYITEM:
      return state.set("playItems", n.playItems.filter(i => i.get("id") !== action.id))
      .set("showDeleteConfirm", false).set("showNew", true);
    case constants.UPDATE_PLAYITEM_STATUS:
      newList = []
      n.playItems.forEach(i => {
        if(i.get("id") === action.id){
          newList.push(i.set("approved", action.status));
        }else{
          newList.push(i)
        }
      })

      return state.set("playItems", fromJS(newList))
      .set("showDeleteConfirm", false).set("showNew", true);  
    case constants.UPDATE_PLAYITEM:
      newList = []
        n.playItems.forEach(i => {
          if(i.get("id") === action.id){
            newList.push(i.set("title", action.title).set("url", action.url).set("approved", 0));
          }else{
            newList.push(i)
          }
        })
  
        return state.set("playItems", newList).set("showNew", true).set("showEdit", false);  
    default:
      return state;
  }
};
