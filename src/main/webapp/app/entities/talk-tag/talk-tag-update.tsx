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
import { getEntity, updateEntity, createEntity, reset } from './talk-tag.reducer';
import { ITalkTag } from 'app/shared/model/talk-tag.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITalkTagUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TalkTagUpdate = (props: ITalkTagUpdateProps) => {
  const [idstalk, setIdstalk] = useState([]);
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { talkTagEntity, talks, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/talk-tag');
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
        ...talkTagEntity,
        ...values,
        talks: mapIdList(values.talks)
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
          <h2 id="conferunsApp.talkTag.home.createOrEditLabel">
            <Translate contentKey="conferunsApp.talkTag.home.createOrEditLabel">Create or edit a TalkTag</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : talkTagEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="talk-tag-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="talk-tag-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="tagLabel" for="talk-tag-tag">
                  <Translate contentKey="conferunsApp.talkTag.tag">Tag</Translate>
                </Label>
                <AvField id="talk-tag-tag" type="text" name="tag" />
              </AvGroup>
              <AvGroup>
                <Label for="talk-tag-talk">
                  <Translate contentKey="conferunsApp.talkTag.talk">Talk</Translate>
                </Label>
                <AvInput
                  id="talk-tag-talk"
                  type="select"
                  multiple
                  className="form-control"
                  name="talks"
                  value={talkTagEntity.talks && talkTagEntity.talks.map(e => e.id)}
                >
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
              <Button tag={Link} id="cancel-save" to="/talk-tag" replace color="info">
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
  talkTagEntity: storeState.talkTag.entity,
  loading: storeState.talkTag.loading,
  updating: storeState.talkTag.updating,
  updateSuccess: storeState.talkTag.updateSuccess
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

export default connect(mapStateToProps, mapDispatchToProps)(TalkTagUpdate);
