import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IFile } from 'app/shared/model/file.model';
import { getEntities as getFiles } from 'app/entities/file/file.reducer';
import { getEntity, updateEntity, createEntity, reset } from './file-review.reducer';
import { IFileReview } from 'app/shared/model/file-review.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IFileReviewUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FileReviewUpdate = (props: IFileReviewUpdateProps) => {
  const [fileId, setFileId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { fileReviewEntity, files, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/file-review');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getFiles();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.date = convertDateTimeToServer(values.date);

    if (errors.length === 0) {
      const entity = {
        ...fileReviewEntity,
        ...values
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="conferunsApp.fileReview.home.createOrEditLabel">
            <Translate contentKey="conferunsApp.fileReview.home.createOrEditLabel">Create or edit a FileReview</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : fileReviewEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="file-review-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="file-review-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="dateLabel" for="file-review-date">
                  <Translate contentKey="conferunsApp.fileReview.date">Date</Translate>
                </Label>
                <AvInput
                  id="file-review-date"
                  type="datetime-local"
                  className="form-control"
                  name="date"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? null : convertDateTimeFromServer(props.fileReviewEntity.date)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="commentLabel" for="file-review-comment">
                  <Translate contentKey="conferunsApp.fileReview.comment">Comment</Translate>
                </Label>
                <AvField id="file-review-comment" type="text" name="comment" />
              </AvGroup>
              <AvGroup>
                <Label id="reviewerLabel" for="file-review-reviewer">
                  <Translate contentKey="conferunsApp.fileReview.reviewer">Reviewer</Translate>
                </Label>
                <AvField id="file-review-reviewer" type="text" name="reviewer" />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="file-review-status">
                  <Translate contentKey="conferunsApp.fileReview.status">Status</Translate>
                </Label>
                <AvInput
                  id="file-review-status"
                  type="select"
                  className="form-control"
                  name="status"
                  value={(!isNew && fileReviewEntity.status) || 'NEED_MORE_REVIEW'}
                >
                  <option value="NEED_MORE_REVIEW">{translate('conferunsApp.FileReviewStatus.NEED_MORE_REVIEW')}</option>
                  <option value="REJECTED">{translate('conferunsApp.FileReviewStatus.REJECTED')}</option>
                  <option value="APPROVED">{translate('conferunsApp.FileReviewStatus.APPROVED')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="file-review-file">
                  <Translate contentKey="conferunsApp.fileReview.file">File</Translate>
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
              <Button tag={Link} id="cancel-save" to="/file-review" replace color="info">
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
};

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

export default connect(mapStateToProps, mapDispatchToProps)(FileReviewUpdate);
