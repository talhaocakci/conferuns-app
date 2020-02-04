import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TalkParticipant from './talk-participant';
import TalkParticipantDetail from './talk-participant-detail';
import TalkParticipantUpdate from './talk-participant-update';
import TalkParticipantDeleteDialog from './talk-participant-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TalkParticipantDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TalkParticipantUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TalkParticipantUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TalkParticipantDetail} />
      <ErrorBoundaryRoute path={match.url} component={TalkParticipant} />
    </Switch>
  </>
);

export default Routes;
