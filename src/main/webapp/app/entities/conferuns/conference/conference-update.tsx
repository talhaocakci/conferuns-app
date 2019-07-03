import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IPlace } from 'app/shared/model/conferuns/place.model';
import { getEntities as getPlaces } from 'app/entities/conferuns/place/place.reducer';
import { ITalk } from 'app/shared/model/conferuns/talk.model';
import { getEntities as getTalks } from 'app/entities/conferuns/talk/talk.reducer';
import { getEntity, updateEntity, createEntity, reset } from './conference.reducer';
import { IConference } from 'app/shared/model/conferuns/conference.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IConferenceUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IConferenceUpdateState {
  isNew: boolean;
  idsplaces: any[];
  idstalks: any[];
}

export class ConferenceUpdate extends React.Component<IConferenceUpdateProps, IConferenceUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idsplaces: [],
      idstalks: [],
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

    this.props.getPlaces();
    this.props.getTalks();
  }

  saveEntity = (event, errors, values) => {
    values.startDate = convertDateTimeToServer(values.startDate);
    values.endDate = convertDateTimeToServer(values.endDate);
    values.lastTalkSubmissionTime = convertDateTimeToServer(values.lastTalkSubmissionTime);

    if (errors.length === 0) {
      const { conferenceEntity } = this.props;
      const entity = {
        ...conferenceEntity,
        ...values,
        places: mapIdList(values.places),
        talks: mapIdList(values.talks)
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/conference');
  };

  render() {
    const { conferenceEntity, places, talks, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="conferunsApp.conferunsConference.home.createOrEditLabel">
              <Translate contentKey="conferunsApp.conferunsConference.home.createOrEditLabel">Create or edit a Conference</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : conferenceEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="conference-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="conferenceIdLabel" for="conferenceId">
                    <Translate contentKey="conferunsApp.conferunsConference.conferenceId">Conference Id</Translate>
                  </Label>
                  <AvField id="conference-conferenceId" type="string" className="form-control" name="conferenceId" />
                </AvGroup>
                <AvGroup>
                  <Label id="mainNameLabel" for="mainName">
                    <Translate contentKey="conferunsApp.conferunsConference.mainName">Main Name</Translate>
                  </Label>
                  <AvField id="conference-mainName" type="text" name="mainName" />
                </AvGroup>
                <AvGroup>
                  <Label id="subNameLabel" for="subName">
                    <Translate contentKey="conferunsApp.conferunsConference.subName">Sub Name</Translate>
                  </Label>
                  <AvField id="conference-subName" type="text" name="subName" />
                </AvGroup>
                <AvGroup>
                  <Label id="mainTopicLabel">
                    <Translate contentKey="conferunsApp.conferunsConference.mainTopic">Main Topic</Translate>
                  </Label>
                  <AvInput
                    id="conference-mainTopic"
                    type="select"
                    className="form-control"
                    name="mainTopic"
                    value={(!isNew && conferenceEntity.mainTopic) || 'SOFTWARE_ENGINEERING'}
                  >
                    <option value="SOFTWARE_ENGINEERING">
                      <Translate contentKey="conferunsApp.ConferenceTopic.SOFTWARE_ENGINEERING" />
                    </option>
                    <option value="FINANCE">
                      <Translate contentKey="conferunsApp.ConferenceTopic.FINANCE" />
                    </option>
                    <option value="SPORTS">
                      <Translate contentKey="conferunsApp.ConferenceTopic.SPORTS" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="subTopicLabel" for="subTopic">
                    <Translate contentKey="conferunsApp.conferunsConference.subTopic">Sub Topic</Translate>
                  </Label>
                  <AvField id="conference-subTopic" type="text" name="subTopic" />
                </AvGroup>
                <AvGroup>
                  <Label id="descriptionLabel" for="description">
                    <Translate contentKey="conferunsApp.conferunsConference.description">Description</Translate>
                  </Label>
                  <AvField id="conference-description" type="text" name="description" />
                </AvGroup>
                <AvGroup>
                  <Label id="isFreeLabel" check>
                    <AvInput id="conference-isFree" type="checkbox" className="form-control" name="isFree" />
                    <Translate contentKey="conferunsApp.conferunsConference.isFree">Is Free</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="languageLabel">
                    <Translate contentKey="conferunsApp.conferunsConference.language">Language</Translate>
                  </Label>
                  <AvInput
                    id="conference-language"
                    type="select"
                    className="form-control"
                    name="language"
                    value={(!isNew && conferenceEntity.language) || 'ENGLISH'}
                  >
                    <option value="ENGLISH">
                      <Translate contentKey="conferunsApp.Language.ENGLISH" />
                    </option>
                    <option value="TURKISH">
                      <Translate contentKey="conferunsApp.Language.TURKISH" />
                    </option>
                    <option value="FRENCH">
                      <Translate contentKey="conferunsApp.Language.FRENCH" />
                    </option>
                    <option value="SPANISH">
                      <Translate contentKey="conferunsApp.Language.SPANISH" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="startDateLabel" for="startDate">
                    <Translate contentKey="conferunsApp.conferunsConference.startDate">Start Date</Translate>
                  </Label>
                  <AvInput
                    id="conference-startDate"
                    type="datetime-local"
                    className="form-control"
                    name="startDate"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.conferenceEntity.startDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="endDateLabel" for="endDate">
                    <Translate contentKey="conferunsApp.conferunsConference.endDate">End Date</Translate>
                  </Label>
                  <AvInput
                    id="conference-endDate"
                    type="datetime-local"
                    className="form-control"
                    name="endDate"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.conferenceEntity.endDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="lastTalkSubmissionTimeLabel" for="lastTalkSubmissionTime">
                    <Translate contentKey="conferunsApp.conferunsConference.lastTalkSubmissionTime">Last Talk Submission Time</Translate>
                  </Label>
                  <AvInput
                    id="conference-lastTalkSubmissionTime"
                    type="datetime-local"
                    className="form-control"
                    name="lastTalkSubmissionTime"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.conferenceEntity.lastTalkSubmissionTime)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="places">
                    <Translate contentKey="conferunsApp.conferunsConference.places">Places</Translate>
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
                  <Label for="talks">
                    <Translate contentKey="conferunsApp.conferunsConference.talks">Talks</Translate>
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
                <Button tag={Link} id="cancel-save" to="/entity/conference" replace color="info">
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
                <Button color="primary" id="save-continue" type="submit">
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.savecontinue">Save Continue</Translate>
                  </span>
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

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ConferenceUpdate);
