import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import App from "./App";
import { library } from "@fortawesome/fontawesome-svg-core";
import {
  faPlay,
  faPause,
  faStop,
  faPlus,
  faMinus,
  faVolumeMute,
  faVolumeUp,
  faThumbsUp,
  faThumbsDown,
  faSave,
  faTimes,
  faEdit,
  faCheck
} from "@fortawesome/free-solid-svg-icons";

library.add(
  faPlay,
  faPause,
  faStop,
  faPlus,
  faMinus,
  faVolumeMute,
  faVolumeUp,
  faThumbsUp,
  faThumbsDown,
  faSave,
  faTimes,
  faEdit,
  faCheck
);

ReactDOM.render(<App />, document.getElementById("root"));
