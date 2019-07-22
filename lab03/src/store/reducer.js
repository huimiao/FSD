import { combineReducers } from "redux-immutable";
import { reducer as playListReducer } from "../components/VideoPlayer/PlayerList/store";
import { reducer as playViewReducer } from "../components/VideoPlayer/PlayerView/store";
import { reducer as playControlReducer } from "../components/VideoPlayer/PlayerControl/store";

const reducer = combineReducers({
  playList: playListReducer,
  playView: playViewReducer,
  playControl: playControlReducer
});

export default reducer;
