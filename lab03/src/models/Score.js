export default class Score{
    likeScore;
    disLikeScore;
  
    constructor(likeScore = 0, disLikeScore = 0) {
      this.likeScore = likeScore || 0;
      this.disLikeScore = disLikeScore || 0;
    }
}