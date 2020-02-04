import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ITalk } from 'app/shared/model/talk.model';
import { getEntities as getTalks } from 'app/entities/talk/talk.reducer';
import { getEntity, updateEntity, createEntity, reset } from './file.reducer';
import { IFile } from 'app/shared/model/file.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IFileUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FileUpdate = (props: IFileUpdateProps) => {
  const [talkId, setTalkId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { fileEntity, talks, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/file');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getTalks();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...fileEntity,
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
          <h2 id="conferunsApp.file.home.createOrEditLabel">
            <Translate contentKey="conferunsApp.file.home.createOrEditLabel">Create or edit a File</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : fileEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="file-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="file-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="file-name">
                  <Translate contentKey="conferunsApp.file.name">Name</Translate>
                </Label>
                <AvField id="file-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="pathLabel" for="file-path">
                  <Translate contentKey="conferunsApp.file.path">Path</Translate>
                </Label>
                <AvField id="file-path" type="text" name="path" />
              </AvGroup>
              <AvGroup>
                <Label id="typeLabel" for="file-type">
                  <Translate contentKey="conferunsApp.file.type">Type</Translate>
                </Label>
                <AvField id="file-type" type="text" name="type" />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="file-status">
                  <Translate contentKey="conferunsApp.file.status">Status</Translate>
                </Label>
                <AvInput
                  id="file-status"
                  type="select"
                  className="form-control"
                  name="status"
                  value={(!isNew && fileEntity.status) || 'DRAFT'}
                >
                  <option value="DRAFT">{translate('conferunsApp.FileStatus.DRAFT')}</option>
                  <option value="IN_REVIEW">{translate('conferunsApp.FileStatus.IN_REVIEW')}</option>
                  <option value="NEED_MORE_REVIEW">{translate('conferunsApp.FileStatus.NEED_MORE_REVIEW')}</option>
                  <option value="REJECTED">{translate('conferunsApp.FileStatus.REJECTED')}</option>
                  <option value="APPROVED">{translate('conferunsApp.FileStatus.APPROVED')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="file-talk">
                  <Translate contentKey="conferunsApp.file.talk">Talk</Translate>
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
              <Button tag={Link} id="cancel-save" to="/file" replace color="info">
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

export default connect(mapStateToProps, mapDispatchToProps)(FileUpdate);
