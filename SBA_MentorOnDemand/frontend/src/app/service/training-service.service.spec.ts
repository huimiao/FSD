import { TestBed } from '@angular/core/testing';

import { TrainingServiceService } from './training-service.service';

describe('TrainingServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: TrainingServiceService = TestBed.get(TrainingServiceService);
    expect(service).toBeTruthy();
  });
});
