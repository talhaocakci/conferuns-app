import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudSearchAction, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './presenter.reducer';
import { IPresenter } from 'app/shared/model/conferuns/presenter.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPresenterProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface IPresenterState {
  search: string;
}

export class Presenter extends React.Component<IPresenterProps, IPresenterState> {
  state: IPresenterState = {
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
    const { presenterList, match } = this.props;
    return (
      <div>
        <h2 id="presenter-heading">
          <Translate contentKey="conferunsApp.conferunsPresenter.home.title">Presenters</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp;
            <Translate contentKey="conferunsApp.conferunsPresenter.home.createLabel">Create new Presenter</Translate>
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
                    placeholder={translate('conferunsApp.conferunsPresenter.home.search')}
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
                  <Translate contentKey="conferunsApp.conferunsPresenter.presenterId">Presenter Id</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.conferunsPresenter.totalTechnicalPoints">Total Technical Points</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.conferunsPresenter.averageTechnicalPoints">Average Technical Points</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.conferunsPresenter.totalSpeakingPoints">Total Speaking Points</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.conferunsPresenter.averageSpeakingPoints">Average Speaking Points</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.conferunsPresenter.totalExcitementPoints">Total Excitement Points</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.conferunsPresenter.averageExcitementPoints">Average Excitement Points</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.conferunsPresenter.user">User</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {presenterList.map((presenter, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${presenter.id}`} color="link" size="sm">
                      {presenter.id}
                    </Button>
                  </td>
                  <td>{presenter.presenterId}</td>
                  <td>{presenter.totalTechnicalPoints}</td>
                  <td>{presenter.averageTechnicalPoints}</td>
                  <td>{presenter.totalSpeakingPoints}</td>
                  <td>{presenter.averageSpeakingPoints}</td>
                  <td>{presenter.totalExcitementPoints}</td>
                  <td>{presenter.averageExcitementPoints}</td>
                  <td>{presenter.userId ? presenter.userId : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${presenter.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${presenter.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${presenter.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ presenter }: IRootState) => ({
  presenterList: presenter.entities
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
)(Presenter);
