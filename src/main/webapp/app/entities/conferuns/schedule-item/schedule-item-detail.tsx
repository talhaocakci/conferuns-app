import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './schedule-item.reducer';
import { IScheduleItem } from 'app/shared/model/conferuns/schedule-item.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IScheduleItemDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ScheduleItemDetail extends React.Component<IScheduleItemDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { scheduleItemEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="conferunsApp.conferunsScheduleItem.detail.title">ScheduleItem</Translate> [<b>{scheduleItemEntity.id}</b>
            ]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="fromTime">
                <Translate contentKey="conferunsApp.conferunsScheduleItem.fromTime">From Time</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={scheduleItemEntity.fromTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="tillTime">
                <Translate contentKey="conferunsApp.conferunsScheduleItem.tillTime">Till Time</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={scheduleItemEntity.tillTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="conferunsApp.conferunsScheduleItem.conference">Conference</Translate>
            </dt>
            <dd>{scheduleItemEntity.conferenceId ? scheduleItemEntity.conferenceId : ''}</dd>
            <dt>
              <Translate contentKey="conferunsApp.conferunsScheduleItem.talk">Talk</Translate>
            </dt>
            <dd>{scheduleItemEntity.talkId ? scheduleItemEntity.talkId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/schedule-item" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/schedule-item/${scheduleItemEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ scheduleItem }: IRootState) => ({
  scheduleItemEntity: scheduleItem.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ScheduleItemDetail);
