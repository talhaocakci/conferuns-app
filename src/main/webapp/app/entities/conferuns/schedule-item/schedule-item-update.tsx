import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IConference } from 'app/shared/model/conferuns/conference.model';
import { getEntities as getConferences } from 'app/entities/conferuns/conference/conference.reducer';
import { ITalk } from 'app/shared/model/conferuns/talk.model';
import { getEntities as getTalks } from 'app/entities/conferuns/talk/talk.reducer';
import { getEntity, updateEntity, createEntity, reset } from './schedule-item.reducer';
import { IScheduleItem } from 'app/shared/model/conferuns/schedule-item.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IScheduleItemUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IScheduleItemUpdateState {
  isNew: boolean;
  conferenceId: string;
  talkId: string;
}

export class ScheduleItemUpdate extends React.Component<IScheduleItemUpdateProps, IScheduleItemUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      conferenceId: '0',
      talkId: '0',
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

    this.props.getConferences();
    this.props.getTalks();
  }

  saveEntity = (event, errors, values) => {
    values.fromTime = convertDateTimeToServer(values.fromTime);
    values.tillTime = convertDateTimeToServer(values.tillTime);

    if (errors.length === 0) {
      const { scheduleItemEntity } = this.props;
      const entity = {
        ...scheduleItemEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/schedule-item');
  };

  render() {
    const { scheduleItemEntity, conferences, talks, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="conferunsApp.conferunsScheduleItem.home.createOrEditLabel">
              <Translate contentKey="conferunsApp.conferunsScheduleItem.home.createOrEditLabel">Create or edit a ScheduleItem</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : scheduleItemEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="schedule-item-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="fromTimeLabel" for="fromTime">
                    <Translate contentKey="conferunsApp.conferunsScheduleItem.fromTime">From Time</Translate>
                  </Label>
                  <AvInput
                    id="schedule-item-fromTime"
                    type="datetime-local"
                    className="form-control"
                    name="fromTime"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.scheduleItemEntity.fromTime)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="tillTimeLabel" for="tillTime">
                    <Translate contentKey="conferunsApp.conferunsScheduleItem.tillTime">Till Time</Translate>
                  </Label>
                  <AvInput
                    id="schedule-item-tillTime"
                    type="datetime-local"
                    className="form-control"
                    name="tillTime"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.scheduleItemEntity.tillTime)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="conference.id">
                    <Translate contentKey="conferunsApp.conferunsScheduleItem.conference">Conference</Translate>
                  </Label>
                  <AvInput id="schedule-item-conference" type="select" className="form-control" name="conferenceId">
                    <option value="" key="0" />
                    {conferences
                      ? conferences.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="talk.id">
                    <Translate contentKey="conferunsApp.conferunsScheduleItem.talk">Talk</Translate>
                  </Label>
                  <AvInput id="schedule-item-talk" type="select" className="form-control" name="talkId">
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
                <Button tag={Link} id="cancel-save" to="/entity/schedule-item" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />&nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />&nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
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
  conferences: storeState.conference.entities,
  talks: storeState.talk.entities,
  scheduleItemEntity: storeState.scheduleItem.entity,
  loading: storeState.scheduleItem.loading,
  updating: storeState.scheduleItem.updating,
  updateSuccess: storeState.scheduleItem.updateSuccess
});

const mapDispatchToProps = {
  getConferences,
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
)(ScheduleItemUpdate);
