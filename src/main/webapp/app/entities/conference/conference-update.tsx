import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IPlace } from 'app/shared/model/place.model';
import { getEntities as getPlaces } from 'app/entities/place/place.reducer';
import { ITalk } from 'app/shared/model/talk.model';
import { getEntities as getTalks } from 'app/entities/talk/talk.reducer';
import { getEntity, updateEntity, createEntity, reset } from './conference.reducer';
import { IConference } from 'app/shared/model/conference.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IConferenceUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ConferenceUpdate = (props: IConferenceUpdateProps) => {
  const [idsplaces, setIdsplaces] = useState([]);
  const [idstalks, setIdstalks] = useState([]);
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { conferenceEntity, places, talks, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/conference');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getPlaces();
    props.getTalks();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.startDate = convertDateTimeToServer(values.startDate);
    values.endDate = convertDateTimeToServer(values.endDate);
    values.lastTalkSubmissionTime = convertDateTimeToServer(values.lastTalkSubmissionTime);

    if (errors.length === 0) {
      const entity = {
        ...conferenceEntity,
        ...values,
        places: mapIdList(values.places),
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
          <h2 id="conferunsApp.conference.home.createOrEditLabel">
            <Translate contentKey="conferunsApp.conference.home.createOrEditLabel">Create or edit a Conference</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : conferenceEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="conference-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="conference-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="conferenceIdLabel" for="conference-conferenceId">
                  <Translate contentKey="conferunsApp.conference.conferenceId">Conference Id</Translate>
                </Label>
                <AvField id="conference-conferenceId" type="string" className="form-control" name="conferenceId" />
              </AvGroup>
              <AvGroup>
                <Label id="mainNameLabel" for="conference-mainName">
                  <Translate contentKey="conferunsApp.conference.mainName">Main Name</Translate>
                </Label>
                <AvField id="conference-mainName" type="text" name="mainName" />
              </AvGroup>
              <AvGroup>
                <Label id="subNameLabel" for="conference-subName">
                  <Translate contentKey="conferunsApp.conference.subName">Sub Name</Translate>
                </Label>
                <AvField id="conference-subName" type="text" name="subName" />
              </AvGroup>
              <AvGroup>
                <Label id="mainTopicLabel" for="conference-mainTopic">
                  <Translate contentKey="conferunsApp.conference.mainTopic">Main Topic</Translate>
                </Label>
                <AvInput
                  id="conference-mainTopic"
                  type="select"
                  className="form-control"
                  name="mainTopic"
                  value={(!isNew && conferenceEntity.mainTopic) || 'SOFTWARE_ENGINEERING'}
                >
                  <option value="SOFTWARE_ENGINEERING">{translate('conferunsApp.ConferenceTopic.SOFTWARE_ENGINEERING')}</option>
                  <option value="FINANCE">{translate('conferunsApp.ConferenceTopic.FINANCE')}</option>
                  <option value="SPORTS">{translate('conferunsApp.ConferenceTopic.SPORTS')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="subTopicLabel" for="conference-subTopic">
                  <Translate contentKey="conferunsApp.conference.subTopic">Sub Topic</Translate>
                </Label>
                <AvField id="conference-subTopic" type="text" name="subTopic" />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="conference-description">
                  <Translate contentKey="conferunsApp.conference.description">Description</Translate>
                </Label>
                <AvField id="conference-description" type="text" name="description" />
              </AvGroup>
              <AvGroup check>
                <Label id="isFreeLabel">
                  <AvInput id="conference-isFree" type="checkbox" className="form-check-input" name="isFree" />
                  <Translate contentKey="conferunsApp.conference.isFree">Is Free</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="languageLabel" for="conference-language">
                  <Translate contentKey="conferunsApp.conference.language">Language</Translate>
                </Label>
                <AvInput
                  id="conference-language"
                  type="select"
                  className="form-control"
                  name="language"
                  value={(!isNew && conferenceEntity.language) || 'ENGLISH'}
                >
                  <option value="ENGLISH">{translate('conferunsApp.Language.ENGLISH')}</option>
                  <option value="TURKISH">{translate('conferunsApp.Language.TURKISH')}</option>
                  <option value="FRENCH">{translate('conferunsApp.Language.FRENCH')}</option>
                  <option value="SPANISH">{translate('conferunsApp.Language.SPANISH')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="startDateLabel" for="conference-startDate">
                  <Translate contentKey="conferunsApp.conference.startDate">Start Date</Translate>
                </Label>
                <AvInput
                  id="conference-startDate"
                  type="datetime-local"
                  className="form-control"
                  name="startDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? null : convertDateTimeFromServer(props.conferenceEntity.startDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="endDateLabel" for="conference-endDate">
                  <Translate contentKey="conferunsApp.conference.endDate">End Date</Translate>
                </Label>
                <AvInput
                  id="conference-endDate"
                  type="datetime-local"
                  className="form-control"
                  name="endDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? null : convertDateTimeFromServer(props.conferenceEntity.endDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="lastTalkSubmissionTimeLabel" for="conference-lastTalkSubmissionTime">
                  <Translate contentKey="conferunsApp.conference.lastTalkSubmissionTime">Last Talk Submission Time</Translate>
                </Label>
                <AvInput
                  id="conference-lastTalkSubmissionTime"
                  type="datetime-local"
                  className="form-control"
                  name="lastTalkSubmissionTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? null : convertDateTimeFromServer(props.conferenceEntity.lastTalkSubmissionTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label for="conference-places">
                  <Translate contentKey="conferunsApp.conference.places">Places</Translate>
                </Label>
                <AvInput
                  id="conference-places"
                  type="select"
                  multiple
                  className="form-control"
                  name="places"
                  value={conferenceEntity.places && conferenceEntity.places.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {places
                    ? places.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="conference-talks">
                  <Translate contentKey="conferunsApp.conference.talks">Talks</Translate>
                </Label>
                <AvInput
                  id="conference-talks"
                  type="select"
                  multiple
                  className="form-control"
                  name="talks"
                  value={conferenceEntity.talks && conferenceEntity.talks.map(e => e.id)}
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
              <Button tag={Link} id="cancel-save" to="/conference" replace color="info">
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
  places: storeState.place.entities,
  talks: storeState.talk.entities,
  conferenceEntity: storeState.conference.entity,
  loading: storeState.conference.loading,
  updating: storeState.conference.updating,
  updateSuccess: storeState.conference.updateSuccess
});

const mapDispatchToProps = {
  getPlaces,
  getTalks,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ConferenceUpdate);
