import React, { Component } from "react";
import { Provider } from "react-redux";
import store from "./store";
import VideoPlayer from "./components/VideoPlayer/VideoPlayer";

class App extends Component {
  render() {
    return (
      <Provider store={store}>
        <VideoPlayer />
      </Provider>
    );
  }
}

export default App;
