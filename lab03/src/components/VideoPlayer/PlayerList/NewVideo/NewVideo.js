import React, { Component, Fragment } from "react";
import { connect } from "react-redux";
import {
  closeNew,
  showEditAction,
  closeEditAction,
  inputValueUpdated,
  setValidated,
  saveNewPlayItem,
  showDeleteConfirmAction,
  closeDeleteConfirmAction,
  deletePlayItem,
  updatePlayItemStatus,
  updatePlayItem
} from "../store/actionCreators";
import { changeVideoPlayingStatus } from "../store/actionCreators";
import { PlayItem } from "../../../../models";

import {
  Button,
  Modal,
  Form,
  Col,
  InputGroup,
  FormControl,
  ButtonToolbar,
  Table
} from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./NewVideo.css";

class NewVideo extends Component {
  constructor(props) {
    super(props);
    this.validated = false;
    this.deleteId = "";
    this.editId = "";
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  inputValueUpdated(type, e) {
    this.props.inputValueUpdated({ [type]: e.target.value });
  }

  deleteConfirm(id){
    this.deleteId = id;
    this.props.showDeleteConfirmAction();
  }

  showEditView(item){
    this.editId = item.get("id");
    this.props.showEditAction(item);
  }

  handleSubmit = (type, which, event) => {
    const form = event.currentTarget;
    if (form.checkValidity() === false) {
      event.preventDefault();
      event.stopPropagation();
    }

    this.props.setValidated({ [which]: true });

    if (type === "new") {
      if (this.validate(this.props.newTitle, this.props.newUrl)) {
        this.props.saveNewPlayItem(
          new PlayItem(this.props.newTitle, this.props.newUrl)
        );
      }
    } else if (type === "edit") {
      if (this.validate(this.props.editTitle, this.props.editUrl)) {
        this.props.updatePlayItem(
          {id: this.editId, title: this.props.editTitle, url: this.props.editUrl}
        );
      }
    }
  };

  validate(title, url) {
    return Boolean(title) && Boolean(url) && url.search(/https?:\/\/\w+/) === 0;
  }

  generateTable() {
    return this.props.playItems.map((item, index) => {
      return (
        <tr key={item.get("id")}>
          <td>{index}</td>
          <td>{item.get("title")}</td>
          <td>{item.get("url")}</td>
          <td>
            <Button variant='primary' onClick={() => this.showEditView(item)}>
              <FontAwesomeIcon icon='edit' />
              <span>Edit</span>
            </Button>
          </td>
          <td>
            <Button variant='danger' onClick={() => this.deleteConfirm(item.get("id"))}>
              <FontAwesomeIcon icon='times' />
              <span>Delete</span>
            </Button>
          </td>
          <td>
            <Button variant='success' disabled={item.get("approved") === 1 ? true: false} 
            onClick={() => this.props.updatePlayItemStatus(item.get("id"), 1)}>
              <FontAwesomeIcon icon='check' />
              <span>Approve</span>
            </Button>
          </td>
        </tr>
      );
    });
  }

  render() {
    return (
      <Fragment>
        <Modal
          show={this.props.showNew}
          size='xl'
          dialogClassName='modal_main'
          scrollable
          onHide={this.props.closeNew}
        >
          <Modal.Header closeButton>
            <Modal.Title>More Videos</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <Form
              noValidate
              validated={this.props.newFormValidate}
            >
              <Form.Row>
                <Form.Group
                  as={Col}
                  controlId='formGridUrl'
                  className='col-sm-4'
                >
                  <InputGroup>
                    <InputGroup.Prepend>
                      <InputGroup.Text
                        id='title'
                        className='input-group-text bg-success text-white'
                      >
                        Title
                      </InputGroup.Text>
                    </InputGroup.Prepend>
                    <FormControl
                      type='text'
                      required
                      placeholder='Title'
                      aria-label='Title'
                      value={this.props.newTitle}
                      onChange={e => this.inputValueUpdated("newTitle", e)}
                    />
                    <Form.Control.Feedback type='invalid'>
                      Please provide a valid Video title.
                    </Form.Control.Feedback>
                  </InputGroup>
                </Form.Group>

                <Form.Group
                  as={Col}
                  controlId='formGridUrl'
                  className='col-sm-7'
                >
                  <InputGroup>
                    <InputGroup.Prepend>
                      <InputGroup.Text
                        id='url'
                        className='input-group-text bg-success text-white'
                      >
                        URL
                      </InputGroup.Text>
                    </InputGroup.Prepend>
                    <Form.Control
                      type='url'
                      pattern='https?://.+'
                      required
                      placeholder='URL'
                      aria-label='URL'
                      value={this.props.newUrl}
                      onChange={e => this.inputValueUpdated("newUrl", e)}
                    />
                    <Form.Control.Feedback type='invalid'>
                      Please provide a valid url.
                    </Form.Control.Feedback>
                  </InputGroup>
                </Form.Group>

                <Form.Group
                  as={Col}
                  controlId='formGridUrl'
                  className='col-sm-1'
                >
                  <ButtonToolbar>
                    <Button
                      type='button'
                      className='btn btn-primary bg-success'
                      onClick={e => this.handleSubmit("new", "newFormValidate", e)}
                    >
                      <FontAwesomeIcon icon='save' />
                      <span>Save</span>
                    </Button>
                  </ButtonToolbar>
                </Form.Group>
              </Form.Row>

              <Table responsive>
                <thead>
                  <tr>
                    <th style={{ width: "5%" }}>S.no</th>
                    <th style={{ width: "20%" }}>Title</th>
                    <th style={{ width: "60%" }}>URL</th>
                    <th colSpan='3' style={{ width: "25%" }}>
                      Action
                    </th>
                  </tr>
                </thead>
                <tbody>{this.generateTable()}</tbody>
              </Table>
            </Form>
          </Modal.Body>
        </Modal>

        <Modal
          show={this.props.showEdit}
          size='md'
          dialogClassName='modal_main'
          onHide={this.props.closeEditAction}
        >
          <Modal.Header closeButton>
            <Modal.Title>Edit Video</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <Form
              noValidate
              validated={this.props.editFormValidate}
            >
              <Form.Group as={Col} controlId='formGridTitle_E'>
                <InputGroup>
                  <InputGroup.Prepend>
                    <InputGroup.Text
                      id='title'
                      className='input-group-text bg-success text-white'
                    >
                      Title
                    </InputGroup.Text>
                  </InputGroup.Prepend>
                  <FormControl
                    required
                    readOnly
                    as='input'
                    type='text'
                    placeholder='Title'
                    aria-label='Title'
                    value={this.props.editTitle}
                    onChange={e => this.inputValueUpdated("editTitle", e)}
                  />
                  <Form.Control.Feedback type='invalid'>
                    Please provide a valid Video title.
                  </Form.Control.Feedback>
                </InputGroup>
              </Form.Group>

              <Form.Group as={Col} controlId='formGridUrl_E'>
                <InputGroup>
                  <InputGroup.Prepend>
                    <InputGroup.Text
                      id='url'
                      className='input-group-text bg-success text-white'
                    >
                      URL
                    </InputGroup.Text>
                  </InputGroup.Prepend>
                  <FormControl
                    type='url'
                    pattern='https?://.+'
                    required
                    placeholder='URL'
                    aria-label='URL'
                    value={this.props.editUrl}
                    onChange={e => this.inputValueUpdated("editUrl", e)}
                  />
                  <Form.Control.Feedback type='invalid'>
                    Please provide a valid url.
                  </Form.Control.Feedback>
                </InputGroup>
              </Form.Group>

              <ButtonToolbar className='mr-1'>
                <Button
                  className='btn btn-primary bg-success mr-2'
                  onClick={() => this.props.closeEditAction()}
                >
                  <span>Cancel</span>
                </Button>{" "}
                <Button type='button' className='btn btn-primary bg-success' 
                  onClick={e => this.handleSubmit("edit", "editFormValidate", e)}>
                  <span>Save</span>
                </Button>
              </ButtonToolbar>
            </Form>
          </Modal.Body>
        </Modal>


        <Modal
          show={this.props.showDeleteConfirm}
          size='md'
          dialogClassName='modal_main'
          onHide={this.props.closeDeleteConfirmAction}
        >
          <Modal.Header closeButton>
            <Modal.Title>Delete Confirmation</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            Are you sure delete?              
          </Modal.Body>
          <Modal.Footer>
          <ButtonToolbar className='mr-1'>
                <Button
                  className='btn btn-primary bg-success mr-2'
                  onClick={this.props.closeDeleteConfirmAction}
                >
                  <span>Cancel</span>
                </Button>{" "}
                <Button type='button' className='btn btn-primary bg-success' 
                onClick={() => this.props.deletePlayItem(this.deleteId)}>
                  <span>OK</span>
                </Button>
              </ButtonToolbar>
          </Modal.Footer>
        </Modal>
      </Fragment>
    );
  }
}

const mapstate = state => ({
  playItems: state.get("playList").get("playItems"),
  showNew: state.get("playList").get("showNew"),
  showEdit: state.get("playList").get("showEdit"),
  newTitle: state.get("playList").get("newTitle"),
  showDeleteConfirm: state.get("playList").get("showDeleteConfirm"),
  newUrl: state.get("playList").get("newUrl"),
  editTitle: state.get("playList").get("editTitle"),
  editUrl: state.get("playList").get("editUrl"),
  editFormValidate: state.get("playList").get("editFormValidate"),
  newFormValidate: state.get("playList").get("newFormValidate")
});

export default connect(
  mapstate,
  {
    closeNew,
    changeVideoPlayingStatus,
    showEditAction,
    closeEditAction,
    inputValueUpdated,
    setValidated,
    saveNewPlayItem,    
    showDeleteConfirmAction,
    closeDeleteConfirmAction,
    deletePlayItem,
    updatePlayItemStatus,
    updatePlayItem
  }
)(NewVideo);
