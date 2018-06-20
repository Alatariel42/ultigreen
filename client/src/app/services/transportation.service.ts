import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { RequestOptions } from '@angular/http';
import { map } from 'rxjs/operators';

import { User, TransportationInfo } from '../models';

@Injectable()
export class TransportationService {
    constructor(private http: HttpClient) { }

    getTransportationFootprint(user: User) {
        return this.http.get('http://localhost:8080/transportation/getTransportationCO2/' + user, {
            params: {}
        }).pipe(map(result => {
            return result
        }));
    }

    getLatestEntry(user: User) {
        return this.http.get<TransportationInfo>('http://localhost:8080/transportation/getLatest/' + user, {
            params: {}
        }).pipe(map(result => {
            return result
        }));
    }

    create(transportationInfo: TransportationInfo) {
        return this.http.post('http://localhost:8080/transportation/addEntry', null, {
            params: {
                username: transportationInfo.username,
                date: String(new Date()),
                distanceFromWork: transportationInfo.distanceFromWork,
                mileageOfCar: transportationInfo.mileageOfCar,
                numCarpools: transportationInfo.numCarpools,
                numTimesDriveToFromWorkWeekly: transportationInfo.numTimesDriveToFromWorkWeekly,
                numTimesBusToFromWOrkWeekly: transportationInfo.numTimesBikeWalkToFromWorkWeekly,
                numTimesBikeWalkToFromWorkWeekly: transportationInfo.numTimesBikeWalkToFromWorkWeekly
            }
        }).pipe(map(user => {
            return user
        }));
    }
}