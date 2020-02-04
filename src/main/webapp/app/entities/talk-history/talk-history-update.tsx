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
import { IPresenter } from 'app/shared/model/presenter.model';
import { getEntities as getPresenters } from 'app/entities/presenter/presenter.reducer';
import { getEntity, updateEntity, createEntity, reset } from './talk-history.reducer';
import { ITalkHistory } from 'app/shared/model/talk-history.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITalkHistoryUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TalkHistoryUpdate = (props: ITalkHistoryUpdateProps) => {
  const [talkId, setTalkId] = useState('0');
  const [presenterId, setPresenterId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { talkHistoryEntity, talks, presenters, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/talk-history');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getTalks();
    props.getPresenters();
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
        ...talkHistoryEntity,
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
          <h2 id="conferunsApp.talkHistory.home.createOrEditLabel">
            <Translate contentKey="conferunsApp.talkHistory.home.createOrEditLabel">Create or edit a TalkHistory</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : talkHistoryEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="talk-history-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="talk-history-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="dateLabel" for="talk-history-date">
                  <Translate contentKey="conferunsApp.talkHistory.date">Date</Translate>
                </Label>
                <AvInput
                  id="talk-history-date"
                  type="datetime-local"
                  className="form-control"
                  name="date"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? null : convertDateTimeFromServer(props.talkHistoryEntity.date)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="totalAudienceLabel" for="talk-history-totalAudience">
                  <Translate contentKey="conferunsApp.talkHistory.totalAudience">Total Audience</Translate>
                </Label>
                <AvField id="talk-history-totalAudience" type="string" className="form-control" name="totalAudience" />
              </AvGroup>
              <AvGroup>
                <Label id="totalTechnicalPointsLabel" for="talk-history-totalTechnicalPoints">
                  <Translate contentKey="conferunsApp.talkHistory.totalTechnicalPoints">Total Technical Points</Translate>
                </Label>
                <AvField id="talk-history-totalTechnicalPoints" type="string" className="form-control" name="totalTechnicalPoints" />
              </AvGroup>
              <AvGroup>
                <Label id="averageTechnicalPointsLabel" for="talk-history-averageTechnicalPoints">
                  <Translate contentKey="conferunsApp.talkHistory.averageTechnicalPoints">Average Technical Points</Translate>
                </Label>
                <AvField id="talk-history-averageTechnicalPoints" type="string" className="form-control" name="averageTechnicalPoints" />
              </AvGroup>
              <AvGroup>
                <Label id="totalSpeakingPointsLabel" for="talk-history-totalSpeakingPoints">
                  <Translate contentKey="conferunsApp.talkHistory.totalSpeakingPoints">Total Speaking Points</Translate>
                </Label>
                <AvField id="talk-history-totalSpeakingPoints" type="string" className="form-control" name="totalSpeakingPoints" />
              </AvGroup>
              <AvGroup>
                <Label id="averageSpeakingPointsLabel" for="talk-history-averageSpeakingPoints">
                  <Translate contentKey="conferunsApp.talkHistory.averageSpeakingPoints">Average Speaking Points</Translate>
                </Label>
                <AvField id="talk-history-averageSpeakingPoints" type="string" className="form-control" name="averageSpeakingPoints" />
              </AvGroup>
              <AvGroup>
                <Label id="totalExcitementPointsLabel" for="talk-history-totalExcitementPoints">
                  <Translate contentKey="conferunsApp.talkHistory.totalExcitementPoints">Total Excitement Points</Translate>
                </Label>
                <AvField id="talk-history-totalExcitementPoints" type="string" className="form-control" name="totalExcitementPoints" />
              </AvGroup>
              <AvGroup>
                <Label id="averageExcitementPointsLabel" for="talk-history-averageExcitementPoints">
                  <Translate contentKey="conferunsApp.talkHistory.averageExcitementPoints">Average Excitement Points</Translate>
                </Label>
                <AvField id="talk-history-averageExcitementPoints" type="string" className="form-control" name="averageExcitementPoints" />
              </AvGroup>
              <AvGroup>
                <Label for="talk-history-talk">
                  <Translate contentKey="conferunsApp.talkHistory.talk">Talk</Translate>
                </Label>
                <AvInput id="talk-history-talk" type="select" className="form-control" name="talkId">
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
              <AvGroup>
                <Label for="talk-history-presenter">
                  <Translate contentKey="conferunsApp.talkHistory.presenter">Presenter</Translate>
                </Label>
                <AvInput id="talk-history-presenter" type="select" className="form-control" name="presenterId">
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
              <Button tag={Link} id="cancel-save" to="/talk-history" replace color="info">
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
  presenters: storeState.presenter.entities,
  talkHistoryEntity: storeState.talkHistory.entity,
  loading: storeState.talkHistory.loading,
  updating: storeState.talkHistory.updating,
  updateSuccess: storeState.talkHistory.updateSuccess
});

const mapDispatchToProps = {
  getTalks,
  getPresenters,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TalkHistoryUpdate);
