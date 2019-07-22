export default class PlayItem {
  id;
  title;
  url;
  status;
  approved = 0;
  likes = 0;
  unlikes = 0;
  currentStatus = "paused";
  exitplayprogress = 0;

  constructor(title, url) {
    this.id = btoa(title);
    this.title = title || "";
    this.url = url || "";
  }
}
