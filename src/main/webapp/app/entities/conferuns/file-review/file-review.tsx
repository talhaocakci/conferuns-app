import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudSearchAction, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './file-review.reducer';
import { IFileReview } from 'app/shared/model/conferuns/file-review.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFileReviewProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface IFileReviewState {
  search: string;
}

export class FileReview extends React.Component<IFileReviewProps, IFileReviewState> {
  state: IFileReviewState = {
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
    const { fileReviewList, match } = this.props;
    return (
      <div>
        <h2 id="file-review-heading">
          <Translate contentKey="conferunsApp.conferunsFileReview.home.title">File Reviews</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="conferunsApp.conferunsFileReview.home.createLabel">Create new File Review</Translate>
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
                    placeholder={translate('conferunsApp.conferunsFileReview.home.search')}
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
                  <Translate contentKey="conferunsApp.conferunsFileReview.date">Date</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.conferunsFileReview.comment">Comment</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.conferunsFileReview.reviewer">Reviewer</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.conferunsFileReview.status">Status</Translate>
                </th>
                <th>
                  <Translate contentKey="conferunsApp.conferunsFileReview.file">File</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {fileReviewList.map((fileReview, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${fileReview.id}`} color="link" size="sm">
                      {fileReview.id}
                    </Button>
                  </td>
                  <td>
                    <TextFormat type="date" value={fileReview.date} format={APP_DATE_FORMAT} />
                  </td>
                  <td>{fileReview.comment}</td>
                  <td>{fileReview.reviewer}</td>
                  <td>
                    <Translate contentKey={`conferunsApp.FileReviewStatus.${fileReview.status}`} />
                  </td>
                  <td>{fileReview.fileId ? <Link to={`file/${fileReview.fileId}`}>{fileReview.fileId}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${fileReview.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${fileReview.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${fileReview.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ fileReview }: IRootState) => ({
  fileReviewList: fileReview.entities
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
)(FileReview);
