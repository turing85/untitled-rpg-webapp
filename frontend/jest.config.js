const { pathsToModuleNameMapper } = require('ts-jest/utils');
const { compilerOptions } = require('./tsconfig');

module.exports = {
  preset: 'jest-preset-angular',
  roots: ['<rootDir>/src/'],
  testMatch: ['**/+(*.)+(spec).+(ts)'],
  setupFilesAfterEnv: ['<rootDir>/src/test.ts'],
  collectCoverage: true,
  coverageReporters: ['html', 'lcov'],
  coverageDirectory: 'test-reports',
  moduleNameMapper: pathsToModuleNameMapper(compilerOptions.paths || {}, {
    prefix: '<rootDir>/'
  }),
  testResultsProcessor: "./node_modules/jest-junit-reporter",
  reporters: [
      "default",
      [
          "./node_modules/jest-html-reporter",
          {
              "outputPath": "./test-reports/test-report.html"
          }
      ]
  ]
};
