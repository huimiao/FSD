import { Score } from "../models";

export default class Repository {
  saveToDB(key, value) {
    localStorage.setItem(key, value);
  }

  loadFromDB(key) {
    const value = localStorage.getItem(key);
    return value;
  }

  loadScoreFromDB(src) {
    if (!src || src === "") {
      return;
    }

    const score = this.loadFromDB(src);

    return (score && JSON.parse(score)) || new Score();
  }

  saveScoreToDB(src, type, score) {
    if (!src || src === "" || !type) {
      return;
    }

    let sc = this.loadScoreFromDB(src);

    if (!(sc.likeScore || sc.disLikeScore)) {
      sc = score;
    }
    if (type === "like") {
      sc.likeScore += 1;
    } else if (type === "disLike") {
      sc.disLikeScore += 1;
    }

    this.saveToDB(src, JSON.stringify(sc));
    return sc;
  }

  lastPlayedVideo() {
    return this.loadFromDB("playing") || "";
  }

  updatePlayItem(id, itemInfo){

  }
}
