import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudSearchAction, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './schedule-item.reducer';
import { IScheduleItem } from 'app/shared/model/conferuns/schedule-item.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IScheduleItemProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface IScheduleItemState {
  search: string;
}

export class ScheduleItem extends React.Component<IScheduleItemProps, IScheduleItemState> {
  state: IScheduleItemState = {
    search: ''
  };

  componentDidMount() {
    this.props.getEntities();
  }

  search = () => {
    if (this.state.search) {
      this.props.getSearchEntities(this.state.search);
    }
  };

  clear = () => {
    this.setState({ search: '' }, () => {
      this.props.getEntities();
    });
  };

  handleSearch = event => this.setState({ search: event.target.value });

  render() {
    const { scheduleItemList, match } = this.props;
    return (
      <div>
        <h2 id="schedule-item-heading">
          <Translate contentKey="conferunsApp.conferunsScheduleItem.home.title">Schedule Items</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="conferunsApp.conferunsScheduleItem.home.createLabel">Create new Schedule Item</Translate>
          </Link>
        </h2>
        <Row>
          <Col sm="12">
            <AvForm onSubmit={this.search}>
              <AvGroup>
                <InputGroup>
                  <AvInput
                    type="text"
                    name="search"
                    value={this.state.search}
                    onChange={this.handleSearch}
                    placeholder={translate('conferunsApp.conferunsScheduleItem.home.search')}
                  />
                  <Button className="input-group-addon">
                    <FontAwesomeIcon icon="search" />
                  </Button>
                  <Button type="reset" className="input-group-addon" onClick={this.clear}>
                    <FontAwesomeIcon icon="trash" />
                  </Button>
                </InputGroup>
              </AvGroup>
            </AvForm>
          </Col>
        </Row>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.conferunsScheduleItem.fromTime">From Time</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.conferunsScheduleItem.tillTime">Till Time</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.conferunsScheduleItem.conference">Conference</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.conferunsScheduleItem.talk">Talk</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {scheduleItemList.map((scheduleItem, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${scheduleItem.id}`} color="link" size="sm">
                      {scheduleItem.id}
                    </Button>
                  </td>
                  <td>
                    <TextFormat type="date" value={scheduleItem.fromTime} format={APP_DATE_FORMAT} />
                  </td>
                  <td>
                    <TextFormat type="date" value={scheduleItem.tillTime} format={APP_DATE_FORMAT} />
                  </td>
                  <td>
                    {scheduleItem.conferenceId ? (
                      <Link to={`conference/${scheduleItem.conferenceId}`}>{scheduleItem.conferenceId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{scheduleItem.talkId ? <Link to={`talk/${scheduleItem.talkId}`}>{scheduleItem.talkId}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${scheduleItem.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${scheduleItem.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${scheduleItem.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ scheduleItem }: IRootState) => ({
  scheduleItemList: scheduleItem.entities
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ScheduleItem);
