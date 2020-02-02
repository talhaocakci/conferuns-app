import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IFile } from 'app/shared/model/conferuns/file.model';
import { getEntities as getFiles } from 'app/entities/conferuns/file/file.reducer';
import { getEntity, updateEntity, createEntity, reset } from './file-review.reducer';
import { IFileReview } from 'app/shared/model/conferuns/file-review.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IFileReviewUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IFileReviewUpdateState {
  isNew: boolean;
  fileId: string;
}

export class FileReviewUpdate extends React.Component<IFileReviewUpdateProps, IFileReviewUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      fileId: '0',
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

    this.props.getFiles();
  }

  saveEntity = (event, errors, values) => {
    values.date = convertDateTimeToServer(values.date);

    if (errors.length === 0) {
      const { fileReviewEntity } = this.props;
      const entity = {
        ...fileReviewEntity,
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
    this.props.history.push('/entity/file-review');
  };

  render() {
    const { fileReviewEntity, files, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="conferunsApp.conferunsFileReview.home.createOrEditLabel">
              <Translate contentKey="conferunsApp.conferunsFileReview.home.createOrEditLabel">Create or edit a FileReview</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : fileReviewEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="file-review-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="dateLabel" for="date">
                    <Translate contentKey="conferunsApp.conferunsFileReview.date">Date</Translate>
                  </Label>
                  <AvInput
                    id="file-review-date"
                    type="datetime-local"
                    className="form-control"
                    name="date"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.fileReviewEntity.date)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="commentLabel" for="comment">
                    <Translate contentKey="conferunsApp.conferunsFileReview.comment">Comment</Translate>
                  </Label>
                  <AvField id="file-review-comment" type="text" name="comment" />
                </AvGroup>
                <AvGroup>
                  <Label id="reviewerLabel" for="reviewer">
                    <Translate contentKey="conferunsApp.conferunsFileReview.reviewer">Reviewer</Translate>
                  </Label>
                  <AvField id="file-review-reviewer" type="text" name="reviewer" />
                </AvGroup>
                <AvGroup>
                  <Label id="statusLabel">
                    <Translate contentKey="conferunsApp.conferunsFileReview.status">Status</Translate>
                  </Label>
                  <AvInput
                    id="file-review-status"
                    type="select"
                    className="form-control"
                    name="status"
                    value={(!isNew && fileReviewEntity.status) || 'NEED_MORE_REVIEW'}
                  >
                    <option value="NEED_MORE_REVIEW">
                      <Translate contentKey="conferunsApp.FileReviewStatus.NEED_MORE_REVIEW" />
                    </option>
                    <option value="REJECTED">
                      <Translate contentKey="conferunsApp.FileReviewStatus.REJECTED" />
                    </option>
                    <option value="APPROVED">
                      <Translate contentKey="conferunsApp.FileReviewStatus.APPROVED" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="file.id">
                    <Translate contentKey="conferunsApp.conferunsFileReview.file">File</Translate>
                  </Label>
                  <AvInput id="file-review-file" type="select" className="form-control" name="fileId">
                    <option value="" key="0" />
                    {files
                      ? files.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/file-review" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />&nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />&nbsp;
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
  files: storeState.file.entities,
  fileReviewEntity: storeState.fileReview.entity,
  loading: storeState.fileReview.loading,
  updating: storeState.fileReview.updating,
  updateSuccess: storeState.fileReview.updateSuccess
});

const mapDispatchToProps = {
  getFiles,
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
)(FileReviewUpdate);
