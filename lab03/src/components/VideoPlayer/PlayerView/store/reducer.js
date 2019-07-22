import { fromJS } from "immutable";
import * as constants from "./constants";

const defaultState = fromJS({
  currentTime: 0,
  duration: 0
});

export default (state = defaultState, action) => {
  switch (action.type) {
    case constants.TIME_UPDATED:
      return state
        .set("currentTime", action.currentTime);
    case constants.DURATION_UPDATED:
        return state.set("duration", action.duration);
    default:
      return state;
  }
};
