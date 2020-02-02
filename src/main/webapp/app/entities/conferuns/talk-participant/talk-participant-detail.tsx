import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './talk-participant.reducer';
import { ITalkParticipant } from 'app/shared/model/conferuns/talk-participant.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITalkParticipantDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class TalkParticipantDetail extends React.Component<ITalkParticipantDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { talkParticipantEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="conferunsApp.conferunsTalkParticipant.detail.title">TalkParticipant</Translate> [<b>
              {talkParticipantEntity.id}
            </b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="checkedIn">
                <Translate contentKey="conferunsApp.conferunsTalkParticipant.checkedIn">Checked In</Translate>
              </span>
            </dt>
            <dd>{talkParticipantEntity.checkedIn ? 'true' : 'false'}</dd>
            <dt>
              <span id="plannedToGo">
                <Translate contentKey="conferunsApp.conferunsTalkParticipant.plannedToGo">Planned To Go</Translate>
              </span>
            </dt>
            <dd>{talkParticipantEntity.plannedToGo ? 'true' : 'false'}</dd>
            <dt>
              <span id="favorited">
                <Translate contentKey="conferunsApp.conferunsTalkParticipant.favorited">Favorited</Translate>
              </span>
            </dt>
            <dd>{talkParticipantEntity.favorited ? 'true' : 'false'}</dd>
            <dt>
              <Translate contentKey="conferunsApp.conferunsTalkParticipant.talks">Talks</Translate>
            </dt>
            <dd>
              {talkParticipantEntity.talks
                ? talkParticipantEntity.talks.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.id}</a>
                      {i === talkParticipantEntity.talks.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
          </dl>
          <Button tag={Link} to="/entity/talk-participant" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/talk-participant/${talkParticipantEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ talkParticipant }: IRootState) => ({
  talkParticipantEntity: talkParticipant.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TalkParticipantDetail);
