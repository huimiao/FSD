import React from "react";
import PropTypes from "prop-types";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

const PlayItem = ({ item, selectedItemId, onPlayItemClicked }) => (
  <li
    key={item.get("id")}
    onClick={() => {
      onPlayItemClicked(item.get("id"));
    }}
    className={item.get("id") === selectedItemId ? "active_list" : ""}
  >
    <div className={item.get("id") === selectedItemId ? "animation" : ""}>
      <FontAwesomeIcon icon='play' />
      <span className={item.get("id") === selectedItemId ? "full_string" : ""}>
        {item.get("title")}
      </span>
    </div>
  </li>
);

PlayItem.propTypes = {
  onPlayItemClicked: PropTypes.func.isRequired
};

export default PlayItem;
