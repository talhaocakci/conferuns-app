import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Talk from './talk';
import TalkDetail from './talk-detail';
import TalkUpdate from './talk-update';
import TalkDeleteDialog from './talk-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TalkUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TalkUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TalkDetail} />
      <ErrorBoundaryRoute path={match.url} component={Talk} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={TalkDeleteDialog} />
  </>
);

export default Routes;
