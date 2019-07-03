import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudSearchAction, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './conference.reducer';
import { IConference } from 'app/shared/model/conferuns/conference.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IConferenceProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface IConferenceState {
  search: string;
}

export class Conference extends React.Component<IConferenceProps, IConferenceState> {
  state: IConferenceState = {
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
    const { conferenceList, match } = this.props;
    return (
      <div>
        <h2 id="conference-heading">
          <Translate contentKey="conferunsApp.conferunsConference.home.title">Conferences</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="conferunsApp.conferunsConference.home.createLabel">Create new Conference</Translate>
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
                    placeholder={translate('conferunsApp.conferunsConference.home.search')}
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
                  <Translate contentKey="conferunsApp.conferunsConference.conferenceId">Conference Id</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.conferunsConference.mainName">Main Name</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.conferunsConference.subName">Sub Name</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.conferunsConference.mainTopic">Main Topic</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.conferunsConference.subTopic">Sub Topic</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.conferunsConference.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.conferunsConference.isFree">Is Free</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.conferunsConference.language">Language</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.conferunsConference.startDate">Start Date</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.conferunsConference.endDate">End Date</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.conferunsConference.lastTalkSubmissionTime">Last Talk Submission Time</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.conferunsConference.places">Places</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.conferunsConference.talks">Talks</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {conferenceList.map((conference, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${conference.id}`} color="link" size="sm">
                      {conference.id}
                    </Button>
                  </td>
                  <td>{conference.conferenceId}</td>
                  <td>{conference.mainName}</td>
                  <td>{conference.subName}</td>
                  <td>
                    <Translate contentKey={`conferunsApp.ConferenceTopic.${conference.mainTopic}`} />
                  </td>
                  <td>{conference.subTopic}</td>
                  <td>{conference.description}</td>
                  <td>{conference.isFree ? 'true' : 'false'}</td>
                  <td>
                    <Translate contentKey={`conferunsApp.Language.${conference.language}`} />
                  </td>
                  <td>
                    <TextFormat type="date" value={conference.startDate} format={APP_DATE_FORMAT} />
                  </td>
                  <td>
                    <TextFormat type="date" value={conference.endDate} format={APP_DATE_FORMAT} />
                  </td>
                  <td>
                    <TextFormat type="date" value={conference.lastTalkSubmissionTime} format={APP_DATE_FORMAT} />
                  </td>
                  <td>
                    {conference.places
                      ? conference.places.map((val, j) => (
                          <span key={j}>
                            <Link to={`place/${val.id}`}>{val.id}</Link>
                            {j === conference.places.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {conference.talks
                      ? conference.talks.map((val, j) => (
                          <span key={j}>
                            <Link to={`talk/${val.id}`}>{val.id}</Link>
                            {j === conference.talks.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${conference.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${conference.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${conference.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ conference }: IRootState) => ({
  conferenceList: conference.entities
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
)(Conference);
