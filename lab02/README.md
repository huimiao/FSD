# Lab02

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 8.0.4.

## Install dependencies

Install the dependencies using npm install on the home directory of Lab02

## Development server

Run `npn run json` for a fake rest api server, it will read the json file playlist.json as rest api. The api link is http://localhost:3500.

## Development server

Run `ng serve --port 3000` for a dev server. Navigate to `http://localhost:3000/`. The app will automatically reload if you change any of the source files.

## Evaluation Check

S.no	|	Activity	|	Check	|	Associate Remark
---	|	---	|	---	|	---
1	|	Created Video Player Parent Component	|	Done	|	
2	|	Created Player Component as a child component of Video Player Component	|	Done	|	
3	|	Created Controls Component as a child component of Video Player Component	|	Done	|	
4	|	Created Playlist Component as a child component of Video Player Component	|	Done	|	
5	|	Implemented Play button as one of the control in Controls Component	|	Done	|	
6	|	Implemented Pause button as one of the control in Controls Component	|	Done	|	
7	|	Implemented Volume Up button as one of the control in Controls Component	|	Done	|	
8	|	Implemented Volume Down button as one of the control in Controls Component	|	Done	|	
9	|	Implemented Reload button as one of the control in Controls Component	|	Done	|	
10	|	Implemented Like button as one of the control in Controls Component	|	Done	|	
11	|	Implemented Unlike button as one of the control in Controls Component	|	Done	|	
12	|	Implemented Play button as one of the control in Controls Component	|	Done	|	
13	|	If video is playing, the play button should be disabled	|	Done	|	
14	|	If video is stopped/paused, the stop/paused button should be disabled	|	Done	|	
15	|	You may use bootstrap glyphicons for icons in video player	|	Done	|	
16	|	When page loads for the first time, the video must not start playing by default	|	Done	|	
17	|	When the page loads after previous exit from the app, it must start with the video which was open when app exited in past.	|	Done	|	
18	|	Clicking on volume up button should increase playback volume	|	Done	|	
19	|	Clicking on volume down button shuld decrease the playback volume	|	Done	|	
20	|	Clicking on reload button should start the video playback from start	|	Done	|	
21	|	Mute/Unmute (headphone) is a toggle button. Clicking on it will be iteratively mute or unmute the sound in video playback.	|	Done	|	
22	|	Progress bar is implemented using HTML5 progress bar element. Progress bar will change the value as per the percentage of video played.	|	Done	|	
23	|	The value for number of likes and un-likes should be saved in local storage and retrieved from the same.	|	Done	|	
24	|	Clicking on like and unlike should increase and decrease the number of likes and unlikes respectively. The values are coming from JSON file (accessed using FAKE Rest API)	|	Done	|	
25	|	Portrait Mode - Playlist will go towards bottom when in portrait mode	|	Done	|	
26	|	Clicking on item in the playlist should play the respective video in the video player, when in portrait mode	|	Done	|	
27	|	ng-Bootstrap is used to make the player responsive	|	Done	|	
28	|	Videos are loaded in playlist by making a call to FAKE REST API created using npm package: json-server	|	Done	|	
29	|	New Feature other than documented	|	Done	|	See below
30	|	Input type is "url" to add new youtube url	|	Done	|	
31	|	It should not accept bad url	|	Done	|	
32	|	Each new URL which is added must be added to JSON file using FAKE REST API	|	Done	|	
33	|	Each new URL which is added must be added to the list of URL's in the table below	|	Done	|	
34	|	User should be able to edit the URL  using FAKE Rest  API	|	Done	|	
35	|	User  should be able to delete the URL using FAKE Rest API	|	Done	|	
36	|	Each URL which is added here must be available in Video Player component only after approval	|	Done	|	
37	|	User will approve the URL by clicking on the link in the table. If the video plays in the new browser window, then it is approved by the user.	|	Done	|	
38	|	Once video is approved, approve button is disabled.	|	Done	|	
39	|	Approve button is enabled again only if video URL is edited	|	Done	|	
40	|	New Feature other than documented	|	Done	|	See below
						

## New Feature other than documented

1. Click the progress bar, video will play start from there
2. When click the video window, video will be stopped or pause/stopped, with styled play button
3. Better appearence
4. Playing list will roll from right to left on the selected video
5. Selected playing list item is highted
6. Hover the play list and buttons will be highted.
7. Show the video play time/total time
8. Video will play from where the last place where leaved
9. Div as button
10. Angular navigation is used


![1](https://github.com/huimiao/FSD/raw/master/lab02/screenshot/1.jpg)

![2](https://github.com/huimiao/FSD/raw/master/lab02/screenshot/2.jpg)

![3](https://github.com/huimiao/FSD/raw/master/lab02/screenshot/3.jpg)

![4](https://github.com/huimiao/FSD/raw/master/lab02/screenshot/4.jpg)

![5](https://github.com/huimiao/FSD/raw/master/lab02/screenshot/5.jpg)
