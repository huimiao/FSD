import React, { Component, Fragment } from "react";
import { PlayerHeader, emitter } from "../common";
import { connect } from "react-redux";
import { Repository } from "../../../services";
import {
  initPlayList,
  playItemSelected,
  showNew
} from "./store/actionCreators";
import PlayItem from "./PlayItem";
import { changeVideoPlayingStatus } from "./store/actionCreators";
import "./PlayerList.css";

class PlayerList extends Component {
  componentDidMount() {
    this.props.initPlayList();
    this.repository = new Repository();
    this.loaded = false;
    this.itemSelected = this.itemSelected.bind(this);
  }

  componentDidUpdate() {
    if (!this.loaded) {
      this.loaded = true;
      let lastPlayedPlayItem;
      const lastPlayedId = this.repository.lastPlayedVideo();

      if (lastPlayedId === "" && this.props.playItems.length !== 0) {
        lastPlayedPlayItem = this.props.playItems.get(0);
      } else {
        lastPlayedPlayItem = this.props.playItems
          .filter(i => i.get("id") === lastPlayedId)
          .get(0);
      }

      if (lastPlayedPlayItem) {
        this.itemSelected(lastPlayedPlayItem.get("id"));
      }
    }
  }

  openNewVideoView() {
    this.props.showNew();
    this.props.changeVideoPlayingStatus("pause");
    emitter.emit("pause");
  }

  itemSelected(id) {
    this.repository.saveToDB("playing", id);
    this.props.playItemSelected(id);

    emitter.emit(
      "itemSelected",
      this.props.playItems.filter(i => i.get("id") === id).get(0)
    );
  }

  getTodoItem() {
    return this.props.playItems
      .filter(item => item.get("approved") === 1)
      .map(item => (
        <PlayItem
          key={item.get("id")}
          item={item}
          selectedItemId={this.props.selectedItemId}
          onPlayItemClicked={this.itemSelected}
        />
      ));
  }

  render() {
    return (
      <Fragment>
        <PlayerHeader title={this.props.title} />
        <main className='player_list'>
          <ul className='list-group play_list_content'>
            {this.getTodoItem()}
            <li onClick={() => this.openNewVideoView()}>Add More</li>
          </ul>
        </main>
        <footer className='bottom-sppliter' />
      </Fragment>
    );
  }
}

const mapState = state => ({
  playItems: state.get("playList").get("playItems"),
  selectedItemId:
    state.get("playList").get("selectedItem") &&
    state
      .get("playList")
      .get("selectedItem")
      .get("id")
      ? state
          .get("playList")
          .get("selectedItem")
          .get("id")
      : ""
});

export default connect(
  mapState,
  { playItemSelected, initPlayList, changeVideoPlayingStatus, showNew }
)(PlayerList);
