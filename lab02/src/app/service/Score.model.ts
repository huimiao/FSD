export class Score {
  likeScore: number;
  disLikeScore: number;

  constructor(likeScore: number = 0, disLikeScore: number = 0) {
    this.likeScore = likeScore || 0;
    this.disLikeScore = disLikeScore || 0;
  }
}
