import React, { Component } from "react";
import PropTypes from 'prop-types';
import "./PlayerHeader.css";

class PlayerHeader extends Component {
  render() {
    return <header className="playerHeader">{this.props.title}</header>;
  }
}

PlayerHeader.propTypes = {
  title: PropTypes.string.isRequired
}

PlayerHeader.defaultProps = {
  title: "Default Title"
}

export default PlayerHeader;
