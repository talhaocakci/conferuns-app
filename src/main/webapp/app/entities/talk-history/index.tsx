import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TalkHistory from './talk-history';
import TalkHistoryDetail from './talk-history-detail';
import TalkHistoryUpdate from './talk-history-update';
import TalkHistoryDeleteDialog from './talk-history-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TalkHistoryDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TalkHistoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TalkHistoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TalkHistoryDetail} />
      <ErrorBoundaryRoute path={match.url} component={TalkHistory} />
    </Switch>
  </>
);

export default Routes;
