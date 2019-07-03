import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ITalk } from 'app/shared/model/conferuns/talk.model';
import { getEntities as getTalks } from 'app/entities/conferuns/talk/talk.reducer';
import { getEntity, updateEntity, createEntity, reset } from './file.reducer';
import { IFile } from 'app/shared/model/conferuns/file.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IFileUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IFileUpdateState {
  isNew: boolean;
  talkId: string;
}

export class FileUpdate extends React.Component<IFileUpdateProps, IFileUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      talkId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getTalks();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { fileEntity } = this.props;
      const entity = {
        ...fileEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/file');
  };

  render() {
    const { fileEntity, talks, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="conferunsApp.conferunsFile.home.createOrEditLabel">
              <Translate contentKey="conferunsApp.conferunsFile.home.createOrEditLabel">Create or edit a File</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : fileEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="file-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    <Translate contentKey="conferunsApp.conferunsFile.name">Name</Translate>
                  </Label>
                  <AvField id="file-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label id="pathLabel" for="path">
                    <Translate contentKey="conferunsApp.conferunsFile.path">Path</Translate>
                  </Label>
                  <AvField id="file-path" type="text" name="path" />
                </AvGroup>
                <AvGroup>
                  <Label id="typeLabel" for="type">
                    <Translate contentKey="conferunsApp.conferunsFile.type">Type</Translate>
                  </Label>
                  <AvField id="file-type" type="text" name="type" />
                </AvGroup>
                <AvGroup>
                  <Label id="statusLabel">
                    <Translate contentKey="conferunsApp.conferunsFile.status">Status</Translate>
                  </Label>
                  <AvInput
                    id="file-status"
                    type="select"
                    className="form-control"
                    name="status"
                    value={(!isNew && fileEntity.status) || 'DRAFT'}
                  >
                    <option value="DRAFT">
                      <Translate contentKey="conferunsApp.FileStatus.DRAFT" />
                    </option>
                    <option value="IN_REVIEW">
                      <Translate contentKey="conferunsApp.FileStatus.IN_REVIEW" />
                    </option>
                    <option value="NEED_MORE_REVIEW">
                      <Translate contentKey="conferunsApp.FileStatus.NEED_MORE_REVIEW" />
                    </option>
                    <option value="REJECTED">
                      <Translate contentKey="conferunsApp.FileStatus.REJECTED" />
                    </option>
                    <option value="APPROVED">
                      <Translate contentKey="conferunsApp.FileStatus.APPROVED" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="talk.id">
                    <Translate contentKey="conferunsApp.conferunsFile.talk">Talk</Translate>
                  </Label>
                  <AvInput id="file-talk" type="select" className="form-control" name="talkId">
                    <option value="" key="0" />
                    {talks
                      ? talks.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/file" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  talks: storeState.talk.entities,
  fileEntity: storeState.file.entity,
  loading: storeState.file.loading,
  updating: storeState.file.updating,
  updateSuccess: storeState.file.updateSuccess
});

const mapDispatchToProps = {
  getTalks,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FileUpdate);
