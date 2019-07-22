import * as constants from "./constants";
import axios from "axios";

export const toggleSpeaker = status => ({
  type: constants.TOGGLE_SPEAKER,
  speakerStatus: status
});

export const updateScore = score => ({
  type: constants.UPDATE_SCORE,
  likeNum: score.likeScore,
  disLikeNum: score.disLikeScore
});

export const updatePlayItem = (id, itemInfo) => {
  return dispathch => {
    axios.patch("http://localhost:3500/youtube/" + id, itemInfo).then(res => {
      return dispathch({
        type: constants.UPDATE_SCORE,
        likeNum: itemInfo.likes,
        disLikeNum: itemInfo.unlikes
      });
    });
  };
};
