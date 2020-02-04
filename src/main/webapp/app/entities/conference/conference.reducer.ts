import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IConference, defaultValue } from 'app/shared/model/conference.model';

export const ACTION_TYPES = {
  FETCH_CONFERENCE_LIST: 'conference/FETCH_CONFERENCE_LIST',
  FETCH_CONFERENCE: 'conference/FETCH_CONFERENCE',
  CREATE_CONFERENCE: 'conference/CREATE_CONFERENCE',
  UPDATE_CONFERENCE: 'conference/UPDATE_CONFERENCE',
  DELETE_CONFERENCE: 'conference/DELETE_CONFERENCE',
  RESET: 'conference/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IConference>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type ConferenceState = Readonly<typeof initialState>;

// Reducer

export default (state: ConferenceState = initialState, action): ConferenceState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CONFERENCE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CONFERENCE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_CONFERENCE):
    case REQUEST(ACTION_TYPES.UPDATE_CONFERENCE):
    case REQUEST(ACTION_TYPES.DELETE_CONFERENCE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_CONFERENCE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CONFERENCE):
    case FAILURE(ACTION_TYPES.CREATE_CONFERENCE):
    case FAILURE(ACTION_TYPES.UPDATE_CONFERENCE):
    case FAILURE(ACTION_TYPES.DELETE_CONFERENCE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_CONFERENCE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_CONFERENCE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_CONFERENCE):
    case SUCCESS(ACTION_TYPES.UPDATE_CONFERENCE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_CONFERENCE):
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

const apiUrl = 'api/conferences';

// Actions

export const getEntities: ICrudGetAllAction<IConference> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_CONFERENCE_LIST,
  payload: axios.get<IConference>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IConference> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CONFERENCE,
    payload: axios.get<IConference>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IConference> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CONFERENCE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IConference> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CONFERENCE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IConference> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CONFERENCE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
