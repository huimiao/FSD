var videoPlayer;
var startBtn;
var pauseBtn;
var reloadBtn;
var volumeUpBtn;
var volumeDownBtn;
var muteBtn;
var likeBtn;
var disLikeBtn;
var speakerIco;
var progressBar;
var currentTimeLable;
var totalTimeLable;
var likeLable;
var disLikeLable;
var playList;

class Score {
	constructor(likeScore, disLikeScore) {
		this.likeScore = likeScore || 0;
		this.disLikeScore = disLikeScore || 0;
	}
}


document.addEventListener("DOMContentLoaded", () => { initialiseMediaPlayer(); }, false);

initialiseMediaPlayer = () => {
	videoPlayer = document.getElementById('player');
	startBtn = document.getElementById('startBtn');
	pauseBtn = document.getElementById('pauseBtn');
	reloadBtn = document.getElementById('reloadBtn');
	volumeUpBtn = document.getElementById('volumeUpBtn');
	volumeDownBtn = document.getElementById('volumeDownBtn');
	speakerBtn = document.getElementById('speakerBtn');
	likeBtn = document.getElementById('likeBtn');
	disLikeBtn = document.getElementById('disLikeBtn');
	speakerIco = document.getElementsByClassName('speakerIco')[0];
	progressBar = document.getElementById('progressBar');
	currentTimeLable = document.getElementById('current_time');
	totalTimeLable = document.getElementById('total_time');
	likeLable = document.getElementById('likeNum');
	disLikeLable = document.getElementById('disLikeNum');
	playList = document.getElementsByClassName('list_item');

	startBtn.addEventListener('click', startToPlay);
	pauseBtn.addEventListener('click', pausePlay);
	reloadBtn.addEventListener('click', reloadPlay);
	volumeUpBtn.addEventListener('click', () => { changeVolume('+') });
	volumeDownBtn.addEventListener('click', () => { changeVolume('-') });
	speakerBtn.addEventListener('click', toggleSpeaker);
	videoPlayer.addEventListener('timeupdate', updateProgress);
	videoPlayer.addEventListener('timeupdate', saveCurrentTimeToDB);
	videoPlayer.addEventListener('canplay', () => updateTime(totalTimeLable, videoPlayer.duration));
	videoPlayer.addEventListener('ended', resetPlayer);
	progressBar.addEventListener('click', goToSpecifiedTime);
	likeBtn.addEventListener('click', () => {
		let score = saveScoreToDB(videoPlayer.src + "score", 'like');
		updateLikeDisLikeLable(likeLable, score.likeScore);
	});
	disLikeBtn.addEventListener('click', () => {
		let score = saveScoreToDB(videoPlayer.src + "score", 'disLike');
		updateLikeDisLikeLable(disLikeLable, score.disLikeScore);
	});

	triggerFirstLoad(playList);
}

startToPlay = () => {
	addStyle(startBtn, 'disabled');
	removeStyle(pauseBtn, 'disabled');
	videoPlayer.play();
}

pausePlay = () => {
	removeStyle(startBtn, 'disabled');
	addStyle(pauseBtn, 'disabled');
	videoPlayer.pause();
}

reloadPlay = () => {
	resetPlayer();
	videoPlayer.pause();
}

changeVolume = tag => {
	let currentVolume = parseFloat(videoPlayer.volume).toFixed(1);
	let adder = (tag === '-') ? -0.1 : 0.1;

	let newVolume = parseFloat(currentVolume) + parseFloat(adder);

	if (newVolume >= 1) {
		newVolume = 1.0;
	} else if (newVolume <= 0) {
		newVolume = 0;
	}

	if (!isFinite(newVolume)) {
		return;
	}

	videoPlayer.volume = newVolume;

	if (videoPlayer.muted && newVolume > 0) {
		openSpeaker();
	} else if (!videoPlayer.muted && newVolume <= 0) {
		muteSpeaker();
	}
}

toggleSpeaker = () => {
	videoPlayer.muted ? openSpeaker() : muteSpeaker();
}

muteSpeaker = () => {
	videoPlayer.muted = true;
	swipIco(speakerIco, 'fa-volume-up', 'fa-volume-mute');
}

openSpeaker = () => {
	videoPlayer.muted = false;
	swipIco(speakerIco, 'fa-volume-mute', 'fa-volume-up');
}

addStyle = (element, style) => {
	element.classList.add(style);
}

removeStyle = (element, style) => {
	element.classList.remove(style);
}

swipIco = (element, oldIco, newIco) => {
	removeStyle(element, oldIco);
	addStyle(element, newIco);
}

updateProgress = () => {
	let percentage = Math.ceil((100 / videoPlayer.duration) * videoPlayer.currentTime);

	if (isNaN(percentage) || !isFinite(percentage)) {
		return;
	}

	progressBar.value = percentage;
	updateTime(currentTimeLable, videoPlayer.currentTime);
}

updateTime = (element, time) => {
	element.innerHTML = formatTime(time);
}

formatTime = time => {
	let h = Math.floor(time / 3600);
	let m = Math.floor(time % 3600 / 60);
	let s = Math.floor(time % 60);
	return padding(h, 2) + ":" + padding(m, 2) + ":" + padding(s, 2);
}

padding = (num, length) => (Array(length).join("0") + num).slice(-length);

loadVideo = (e, path) => {
	let listEl = document.getElementsByClassName('list_item');

	Array.from(listEl)
		.forEach(
			element => removeStyle(element, 'active_list')
		);

	resetPlayer();

	addStyle(getSelectedListItem(e.target), 'active_list');
	videoPlayer.src = path;
	videoPlayer.load();

	let score = loadScoreFromDB(videoPlayer.src + "score");
	updateLikeDisLikeLable(likeLable, score.likeScore);
	updateLikeDisLikeLable(disLikeLable, score.disLikeScore);
	welcomeBack();
}

getSelectedListItem = el => {
	if (el.classList.contains('list_item')) {
		return el;
	} else {
		return getSelectedListItem(el.parentNode)
	}
}

goToSpecifiedTime = e => {
	let to = e.offsetX;
	let width = e.target.offsetWidth;
	let time = to / width * videoPlayer.duration;

	if (isFinite(time)) {
		videoPlayer.currentTime = time;
	}
}

resetPlayer = () => {
	progressBar.value = 0;
	videoPlayer.currentTime = 0;
	removeStyle(startBtn, 'disabled');
	removeStyle(pauseBtn, 'disabled');
	addStyle(pauseBtn, 'disabled');
}

saveToDB = (key, value) => {
	localStorage.setItem(key, value);
}

loadFromDB = key => {
	let value = localStorage.getItem(key);
	return value;
}

loadScoreFromDB = src => {
	if (!src || src === '') {
		return;
	}

	let score = loadFromDB(src);

	return (score && JSON.parse(score)) || new Score();
}

saveScoreToDB = (src, type) => {
	if (!src || src === '' || !type) {
		return;
	}

	let score = loadScoreFromDB(src);
	if (type === 'like') {
		score.likeScore += 1;
	} else if (type === 'disLike') {
		score.disLikeScore += 1;
	}

	saveToDB(src, JSON.stringify(score));
	return score;
}

updateLikeDisLikeLable = (lable, score) => {
	lable.innerHTML = score;
}

triggerFirstLoad = list => {
	if (list.length > 0) {
		list[0].click();
	}
}

saveCurrentTimeToDB = () => {
	saveToDB(videoPlayer.src + "time", videoPlayer.currentTime);
}

welcomeBack = () => {
	let lastWatchingTime = loadFromDB(videoPlayer.src + "time");

	if (lastWatchingTime && isFinite(lastWatchingTime)) {
		videoPlayer.currentTime = lastWatchingTime;
	}
}
