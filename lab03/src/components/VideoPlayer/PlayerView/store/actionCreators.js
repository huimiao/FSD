import * as constants from "./constants";

export const timeUpdated = currentTime => ({
  type: constants.TIME_UPDATED,
  currentTime
});

export const durationUpdated = duration => ({
  type: constants.DURATION_UPDATED,
  duration
});
