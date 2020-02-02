import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudSearchAction, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './talk-history.reducer';
import { ITalkHistory } from 'app/shared/model/conferuns/talk-history.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITalkHistoryProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface ITalkHistoryState {
  search: string;
}

export class TalkHistory extends React.Component<ITalkHistoryProps, ITalkHistoryState> {
  state: ITalkHistoryState = {
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
    const { talkHistoryList, match } = this.props;
    return (
      <div>
        <h2 id="talk-history-heading">
          <Translate contentKey="conferunsApp.conferunsTalkHistory.home.title">Talk Histories</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp;
            <Translate contentKey="conferunsApp.conferunsTalkHistory.home.createLabel">Create new Talk History</Translate>
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
                    placeholder={translate('conferunsApp.conferunsTalkHistory.home.search')}
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
                  <Translate contentKey="conferunsApp.conferunsTalkHistory.date">Date</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.conferunsTalkHistory.totalAudience">Total Audience</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.conferunsTalkHistory.totalTechnicalPoints">Total Technical Points</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.conferunsTalkHistory.averageTechnicalPoints">Average Technical Points</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.conferunsTalkHistory.totalSpeakingPoints">Total Speaking Points</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.conferunsTalkHistory.averageSpeakingPoints">Average Speaking Points</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.conferunsTalkHistory.totalExcitementPoints">Total Excitement Points</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.conferunsTalkHistory.averageExcitementPoints">Average Excitement Points</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.conferunsTalkHistory.presenter">Presenter</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.conferunsTalkHistory.talk">Talk</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {talkHistoryList.map((talkHistory, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${talkHistory.id}`} color="link" size="sm">
                      {talkHistory.id}
                    </Button>
                  </td>
                  <td>
                    <TextFormat type="date" value={talkHistory.date} format={APP_DATE_FORMAT} />
                  </td>
                  <td>{talkHistory.totalAudience}</td>
                  <td>{talkHistory.totalTechnicalPoints}</td>
                  <td>{talkHistory.averageTechnicalPoints}</td>
                  <td>{talkHistory.totalSpeakingPoints}</td>
                  <td>{talkHistory.averageSpeakingPoints}</td>
                  <td>{talkHistory.totalExcitementPoints}</td>
                  <td>{talkHistory.averageExcitementPoints}</td>
                  <td>
                    {talkHistory.presenterId ? <Link to={`presenter/${talkHistory.presenterId}`}>{talkHistory.presenterId}</Link> : ''}
                  </td>
                  <td>{talkHistory.talkId ? <Link to={`talk/${talkHistory.talkId}`}>{talkHistory.talkId}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${talkHistory.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${talkHistory.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${talkHistory.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ talkHistory }: IRootState) => ({
  talkHistoryList: talkHistory.entities
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
)(TalkHistory);
