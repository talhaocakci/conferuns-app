import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IPresenter } from 'app/shared/model/presenter.model';
import { getEntities as getPresenters } from 'app/entities/presenter/presenter.reducer';
import { ITalkParticipant } from 'app/shared/model/talk-participant.model';
import { getEntities as getTalkParticipants } from 'app/entities/talk-participant/talk-participant.reducer';
import { IConference } from 'app/shared/model/conference.model';
import { getEntities as getConferences } from 'app/entities/conference/conference.reducer';
import { ITalkTag } from 'app/shared/model/talk-tag.model';
import { getEntities as getTalkTags } from 'app/entities/talk-tag/talk-tag.reducer';
import { getEntity, updateEntity, createEntity, reset } from './talk.reducer';
import { ITalk } from 'app/shared/model/talk.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITalkUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TalkUpdate = (props: ITalkUpdateProps) => {
  const [presenterId, setPresenterId] = useState('0');
  const [participantsId, setParticipantsId] = useState('0');
  const [conferencesId, setConferencesId] = useState('0');
  const [tagsId, setTagsId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { talkEntity, presenters, talkParticipants, conferences, talkTags, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/talk');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getPresenters();
    props.getTalkParticipants();
    props.getConferences();
    props.getTalkTags();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...talkEntity,
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
          <h2 id="conferunsApp.talk.home.createOrEditLabel">
            <Translate contentKey="conferunsApp.talk.home.createOrEditLabel">Create or edit a Talk</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : talkEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="talk-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="talk-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="languageLabel" for="talk-language">
                  <Translate contentKey="conferunsApp.talk.language">Language</Translate>
                </Label>
                <AvInput
                  id="talk-language"
                  type="select"
                  className="form-control"
                  name="language"
                  value={(!isNew && talkEntity.language) || 'ENGLISH'}
                >
                  <option value="ENGLISH">{translate('conferunsApp.Language.ENGLISH')}</option>
                  <option value="TURKISH">{translate('conferunsApp.Language.TURKISH')}</option>
                  <option value="FRENCH">{translate('conferunsApp.Language.FRENCH')}</option>
                  <option value="SPANISH">{translate('conferunsApp.Language.SPANISH')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="mainTopicLabel" for="talk-mainTopic">
                  <Translate contentKey="conferunsApp.talk.mainTopic">Main Topic</Translate>
                </Label>
                <AvField id="talk-mainTopic" type="text" name="mainTopic" />
              </AvGroup>
              <AvGroup>
                <Label id="subTopicLabel" for="talk-subTopic">
                  <Translate contentKey="conferunsApp.talk.subTopic">Sub Topic</Translate>
                </Label>
                <AvField id="talk-subTopic" type="text" name="subTopic" />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="talk-status">
                  <Translate contentKey="conferunsApp.talk.status">Status</Translate>
                </Label>
                <AvInput
                  id="talk-status"
                  type="select"
                  className="form-control"
                  name="status"
                  value={(!isNew && talkEntity.status) || 'DRAFT'}
                >
                  <option value="DRAFT">{translate('conferunsApp.TalkStatus.DRAFT')}</option>
                  <option value="IN_REVIEW">{translate('conferunsApp.TalkStatus.IN_REVIEW')}</option>
                  <option value="NEED_MORE_REVIEW">{translate('conferunsApp.TalkStatus.NEED_MORE_REVIEW')}</option>
                  <option value="REJECTED">{translate('conferunsApp.TalkStatus.REJECTED')}</option>
                  <option value="APPROVED">{translate('conferunsApp.TalkStatus.APPROVED')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="talk-presenter">
                  <Translate contentKey="conferunsApp.talk.presenter">Presenter</Translate>
                </Label>
                <AvInput id="talk-presenter" type="select" className="form-control" name="presenterId">
                  <option value="" key="0" />
                  {presenters
                    ? presenters.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/talk" replace color="info">
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
  presenters: storeState.presenter.entities,
  talkParticipants: storeState.talkParticipant.entities,
  conferences: storeState.conference.entities,
  talkTags: storeState.talkTag.entities,
  talkEntity: storeState.talk.entity,
  loading: storeState.talk.loading,
  updating: storeState.talk.updating,
  updateSuccess: storeState.talk.updateSuccess
});

const mapDispatchToProps = {
  getPresenters,
  getTalkParticipants,
  getConferences,
  getTalkTags,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TalkUpdate);
