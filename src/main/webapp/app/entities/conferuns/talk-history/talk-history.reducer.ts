import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITalkHistory, defaultValue } from 'app/shared/model/conferuns/talk-history.model';

export const ACTION_TYPES = {
  SEARCH_TALKHISTORIES: 'talkHistory/SEARCH_TALKHISTORIES',
  FETCH_TALKHISTORY_LIST: 'talkHistory/FETCH_TALKHISTORY_LIST',
  FETCH_TALKHISTORY: 'talkHistory/FETCH_TALKHISTORY',
  CREATE_TALKHISTORY: 'talkHistory/CREATE_TALKHISTORY',
  UPDATE_TALKHISTORY: 'talkHistory/UPDATE_TALKHISTORY',
  DELETE_TALKHISTORY: 'talkHistory/DELETE_TALKHISTORY',
  RESET: 'talkHistory/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITalkHistory>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type TalkHistoryState = Readonly<typeof initialState>;

// Reducer

export default (state: TalkHistoryState = initialState, action): TalkHistoryState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_TALKHISTORIES):
    case REQUEST(ACTION_TYPES.FETCH_TALKHISTORY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TALKHISTORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TALKHISTORY):
    case REQUEST(ACTION_TYPES.UPDATE_TALKHISTORY):
    case REQUEST(ACTION_TYPES.DELETE_TALKHISTORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_TALKHISTORIES):
    case FAILURE(ACTION_TYPES.FETCH_TALKHISTORY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TALKHISTORY):
    case FAILURE(ACTION_TYPES.CREATE_TALKHISTORY):
    case FAILURE(ACTION_TYPES.UPDATE_TALKHISTORY):
    case FAILURE(ACTION_TYPES.DELETE_TALKHISTORY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_TALKHISTORIES):
    case SUCCESS(ACTION_TYPES.FETCH_TALKHISTORY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_TALKHISTORY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TALKHISTORY):
    case SUCCESS(ACTION_TYPES.UPDATE_TALKHISTORY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TALKHISTORY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/talk-histories';
const apiSearchUrl = 'api/_search/talk-histories';

// Actions

export const getSearchEntities: ICrudSearchAction<ITalkHistory> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_TALKHISTORIES,
  payload: axios.get<ITalkHistory>(`${apiSearchUrl}?query=${query}`)
});

export const getEntities: ICrudGetAllAction<ITalkHistory> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_TALKHISTORY_LIST,
  payload: axios.get<ITalkHistory>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ITalkHistory> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TALKHISTORY,
    payload: axios.get<ITalkHistory>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITalkHistory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TALKHISTORY,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITalkHistory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TALKHISTORY,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITalkHistory> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TALKHISTORY,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
