import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './conference.reducer';
import { IConference } from 'app/shared/model/conferuns/conference.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IConferenceDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ConferenceDetail extends React.Component<IConferenceDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { conferenceEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="conferunsApp.conferunsConference.detail.title">Conference</Translate> [<b>{conferenceEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="conferenceId">
                <Translate contentKey="conferunsApp.conferunsConference.conferenceId">Conference Id</Translate>
              </span>
            </dt>
            <dd>{conferenceEntity.conferenceId}</dd>
            <dt>
              <span id="mainName">
                <Translate contentKey="conferunsApp.conferunsConference.mainName">Main Name</Translate>
              </span>
            </dt>
            <dd>{conferenceEntity.mainName}</dd>
            <dt>
              <span id="subName">
                <Translate contentKey="conferunsApp.conferunsConference.subName">Sub Name</Translate>
              </span>
            </dt>
            <dd>{conferenceEntity.subName}</dd>
            <dt>
              <span id="mainTopic">
                <Translate contentKey="conferunsApp.conferunsConference.mainTopic">Main Topic</Translate>
              </span>
            </dt>
            <dd>{conferenceEntity.mainTopic}</dd>
            <dt>
              <span id="subTopic">
                <Translate contentKey="conferunsApp.conferunsConference.subTopic">Sub Topic</Translate>
              </span>
            </dt>
            <dd>{conferenceEntity.subTopic}</dd>
            <dt>
              <span id="description">
                <Translate contentKey="conferunsApp.conferunsConference.description">Description</Translate>
              </span>
            </dt>
            <dd>{conferenceEntity.description}</dd>
            <dt>
              <span id="isFree">
                <Translate contentKey="conferunsApp.conferunsConference.isFree">Is Free</Translate>
              </span>
            </dt>
            <dd>{conferenceEntity.isFree ? 'true' : 'false'}</dd>
            <dt>
              <span id="language">
                <Translate contentKey="conferunsApp.conferunsConference.language">Language</Translate>
              </span>
            </dt>
            <dd>{conferenceEntity.language}</dd>
            <dt>
              <span id="startDate">
                <Translate contentKey="conferunsApp.conferunsConference.startDate">Start Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={conferenceEntity.startDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="endDate">
                <Translate contentKey="conferunsApp.conferunsConference.endDate">End Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={conferenceEntity.endDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="lastTalkSubmissionTime">
                <Translate contentKey="conferunsApp.conferunsConference.lastTalkSubmissionTime">Last Talk Submission Time</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={conferenceEntity.lastTalkSubmissionTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="conferunsApp.conferunsConference.places">Places</Translate>
            </dt>
            <dd>
              {conferenceEntity.places
                ? conferenceEntity.places.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.id}</a>
                      {i === conferenceEntity.places.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
            <dt>
              <Translate contentKey="conferunsApp.conferunsConference.talks">Talks</Translate>
            </dt>
            <dd>
              {conferenceEntity.talks
                ? conferenceEntity.talks.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.id}</a>
                      {i === conferenceEntity.talks.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
          </dl>
          <Button tag={Link} to="/entity/conference" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/conference/${conferenceEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ conference }: IRootState) => ({
  conferenceEntity: conference.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ConferenceDetail);
