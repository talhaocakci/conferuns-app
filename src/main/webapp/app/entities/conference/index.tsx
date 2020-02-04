import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Conference from './conference';
import ConferenceDetail from './conference-detail';
import ConferenceUpdate from './conference-update';
import ConferenceDeleteDialog from './conference-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ConferenceDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ConferenceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ConferenceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ConferenceDetail} />
      <ErrorBoundaryRoute path={match.url} component={Conference} />
    </Switch>
  </>
);

export default Routes;
