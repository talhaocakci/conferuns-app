import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudSearchAction, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './talk.reducer';
import { ITalk } from 'app/shared/model/conferuns/talk.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITalkProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface ITalkState {
  search: string;
}

export class Talk extends React.Component<ITalkProps, ITalkState> {
  state: ITalkState = {
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
    const { talkList, match } = this.props;
    return (
      <div>
        <h2 id="talk-heading">
          <Translate contentKey="conferunsApp.conferunsTalk.home.title">Talks</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="conferunsApp.conferunsTalk.home.createLabel">Create new Talk</Translate>
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
                    placeholder={translate('conferunsApp.conferunsTalk.home.search')}
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
                  <Translate contentKey="conferunsApp.conferunsTalk.language">Language</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.conferunsTalk.mainTopic">Main Topic</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.conferunsTalk.subTopic">Sub Topic</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.conferunsTalk.status">Status</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.conferunsTalk.presenter">Presenter</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {talkList.map((talk, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${talk.id}`} color="link" size="sm">
                      {talk.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`conferunsApp.Language.${talk.language}`} />
                  </td>
                  <td>{talk.mainTopic}</td>
                  <td>{talk.subTopic}</td>
                  <td>
                    <Translate contentKey={`conferunsApp.TalkStatus.${talk.status}`} />
                  </td>
                  <td>{talk.presenterId ? <Link to={`presenter/${talk.presenterId}`}>{talk.presenterId}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${talk.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${talk.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${talk.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ talk }: IRootState) => ({
  talkList: talk.entities
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
)(Talk);
