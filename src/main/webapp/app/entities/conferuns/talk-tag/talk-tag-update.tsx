import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ITalk } from 'app/shared/model/conferuns/talk.model';
import { getEntities as getTalks } from 'app/entities/conferuns/talk/talk.reducer';
import { getEntity, updateEntity, createEntity, reset } from './talk-tag.reducer';
import { ITalkTag } from 'app/shared/model/conferuns/talk-tag.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITalkTagUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ITalkTagUpdateState {
  isNew: boolean;
  idstalk: any[];
}

export class TalkTagUpdate extends React.Component<ITalkTagUpdateProps, ITalkTagUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idstalk: [],
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

    this.props.getTalks();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { talkTagEntity } = this.props;
      const entity = {
        ...talkTagEntity,
        ...values,
        talks: mapIdList(values.talks)
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/talk-tag');
  };

  render() {
    const { talkTagEntity, talks, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="conferunsApp.conferunsTalkTag.home.createOrEditLabel">
              <Translate contentKey="conferunsApp.conferunsTalkTag.home.createOrEditLabel">Create or edit a TalkTag</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : talkTagEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="talk-tag-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="tagLabel" for="tag">
                    <Translate contentKey="conferunsApp.conferunsTalkTag.tag">Tag</Translate>
                  </Label>
                  <AvField id="talk-tag-tag" type="text" name="tag" />
                </AvGroup>
                <AvGroup>
                  <Label for="talks">
                    <Translate contentKey="conferunsApp.conferunsTalkTag.talk">Talk</Translate>
                  </Label>
                  <AvInput
                    id="talk-tag-talk"
                    type="select"
                    multiple
                    className="form-control"
                    name="talks"
                    value={talkTagEntity.talks && talkTagEntity.talks.map(e => e.id)}
                  >
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
                <Button tag={Link} id="cancel-save" to="/entity/talk-tag" replace color="info">
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
  talks: storeState.talk.entities,
  talkTagEntity: storeState.talkTag.entity,
  loading: storeState.talkTag.loading,
  updating: storeState.talkTag.updating,
  updateSuccess: storeState.talkTag.updateSuccess
});

const mapDispatchToProps = {
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
)(TalkTagUpdate);
