package com.google.firebase.installations.local;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

// Generated by com.google.auto.value.processor.AutoValueProcessor
final class AutoValue_PersistedInstallationEntry extends PersistedInstallationEntry {

  private final String firebaseInstallationId;

  private final PersistedInstallation.RegistrationStatus registrationStatus;

  private final String authToken;

  private final String refreshToken;

  private final long expiresInSecs;

  private final long tokenCreationEpochInSecs;

  private final String fisError;

  private AutoValue_PersistedInstallationEntry(
      @Nullable String firebaseInstallationId,
      PersistedInstallation.RegistrationStatus registrationStatus,
      @Nullable String authToken,
      @Nullable String refreshToken,
      long expiresInSecs,
      long tokenCreationEpochInSecs,
      @Nullable String fisError) {
    this.firebaseInstallationId = firebaseInstallationId;
    this.registrationStatus = registrationStatus;
    this.authToken = authToken;
    this.refreshToken = refreshToken;
    this.expiresInSecs = expiresInSecs;
    this.tokenCreationEpochInSecs = tokenCreationEpochInSecs;
    this.fisError = fisError;
  }

  @Nullable
  @Override
  public String getFirebaseInstallationId() {
    return firebaseInstallationId;
  }

  @NonNull
  @Override
  public PersistedInstallation.RegistrationStatus getRegistrationStatus() {
    return registrationStatus;
  }

  @Nullable
  @Override
  public String getAuthToken() {
    return authToken;
  }

  @Nullable
  @Override
  public String getRefreshToken() {
    return refreshToken;
  }

  @Override
  public long getExpiresInSecs() {
    return expiresInSecs;
  }

  @Override
  public long getTokenCreationEpochInSecs() {
    return tokenCreationEpochInSecs;
  }

  @Nullable
  @Override
  public String getFisError() {
    return fisError;
  }

  @Override
  public String toString() {
    return "PersistedInstallationEntry{"
        + "firebaseInstallationId=" + firebaseInstallationId + ", "
        + "registrationStatus=" + registrationStatus + ", "
        + "authToken=" + authToken + ", "
        + "refreshToken=" + refreshToken + ", "
        + "expiresInSecs=" + expiresInSecs + ", "
        + "tokenCreationEpochInSecs=" + tokenCreationEpochInSecs + ", "
        + "fisError=" + fisError
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof PersistedInstallationEntry) {
      PersistedInstallationEntry that = (PersistedInstallationEntry) o;
      return (this.firebaseInstallationId == null ? that.getFirebaseInstallationId() == null : this.firebaseInstallationId.equals(that.getFirebaseInstallationId()))
          && this.registrationStatus.equals(that.getRegistrationStatus())
          && (this.authToken == null ? that.getAuthToken() == null : this.authToken.equals(that.getAuthToken()))
          && (this.refreshToken == null ? that.getRefreshToken() == null : this.refreshToken.equals(that.getRefreshToken()))
          && this.expiresInSecs == that.getExpiresInSecs()
          && this.tokenCreationEpochInSecs == that.getTokenCreationEpochInSecs()
          && (this.fisError == null ? that.getFisError() == null : this.fisError.equals(that.getFisError()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h$ = 1;
    h$ *= 1000003;
    h$ ^= (firebaseInstallationId == null) ? 0 : firebaseInstallationId.hashCode();
    h$ *= 1000003;
    h$ ^= registrationStatus.hashCode();
    h$ *= 1000003;
    h$ ^= (authToken == null) ? 0 : authToken.hashCode();
    h$ *= 1000003;
    h$ ^= (refreshToken == null) ? 0 : refreshToken.hashCode();
    h$ *= 1000003;
    h$ ^= (int) ((expiresInSecs >>> 32) ^ expiresInSecs);
    h$ *= 1000003;
    h$ ^= (int) ((tokenCreationEpochInSecs >>> 32) ^ tokenCreationEpochInSecs);
    h$ *= 1000003;
    h$ ^= (fisError == null) ? 0 : fisError.hashCode();
    return h$;
  }

  @Override
  public PersistedInstallationEntry.Builder toBuilder() {
    return new Builder(this);
  }

  static final class Builder extends PersistedInstallationEntry.Builder {
    private String firebaseInstallationId;
    private PersistedInstallation.RegistrationStatus registrationStatus;
    private String authToken;
    private String refreshToken;
    private Long expiresInSecs;
    private Long tokenCreationEpochInSecs;
    private String fisError;
    Builder() {
    }
    private Builder(PersistedInstallationEntry source) {
      this.firebaseInstallationId = source.getFirebaseInstallationId();
      this.registrationStatus = source.getRegistrationStatus();
      this.authToken = source.getAuthToken();
      this.refreshToken = source.getRefreshToken();
      this.expiresInSecs = source.getExpiresInSecs();
      this.tokenCreationEpochInSecs = source.getTokenCreationEpochInSecs();
      this.fisError = source.getFisError();
    }
    @Override
    public PersistedInstallationEntry.Builder setFirebaseInstallationId(String firebaseInstallationId) {
      this.firebaseInstallationId = firebaseInstallationId;
      return this;
    }
    @Override
    public PersistedInstallationEntry.Builder setRegistrationStatus(PersistedInstallation.RegistrationStatus registrationStatus) {
      if (registrationStatus == null) {
        throw new NullPointerException("Null registrationStatus");
      }
      this.registrationStatus = registrationStatus;
      return this;
    }
    @Override
    public PersistedInstallationEntry.Builder setAuthToken(@Nullable String authToken) {
      this.authToken = authToken;
      return this;
    }
    @Override
    public PersistedInstallationEntry.Builder setRefreshToken(@Nullable String refreshToken) {
      this.refreshToken = refreshToken;
      return this;
    }
    @Override
    public PersistedInstallationEntry.Builder setExpiresInSecs(long expiresInSecs) {
      this.expiresInSecs = expiresInSecs;
      return this;
    }
    @Override
    public PersistedInstallationEntry.Builder setTokenCreationEpochInSecs(long tokenCreationEpochInSecs) {
      this.tokenCreationEpochInSecs = tokenCreationEpochInSecs;
      return this;
    }
    @Override
    public PersistedInstallationEntry.Builder setFisError(@Nullable String fisError) {
      this.fisError = fisError;
      return this;
    }
    @Override
    public PersistedInstallationEntry build() {
      if (this.registrationStatus == null
          || this.expiresInSecs == null
          || this.tokenCreationEpochInSecs == null) {
        StringBuilder missing = new StringBuilder();
        if (this.registrationStatus == null) {
          missing.append(" registrationStatus");
        }
        if (this.expiresInSecs == null) {
          missing.append(" expiresInSecs");
        }
        if (this.tokenCreationEpochInSecs == null) {
          missing.append(" tokenCreationEpochInSecs");
        }
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_PersistedInstallationEntry(
          this.firebaseInstallationId,
          this.registrationStatus,
          this.authToken,
          this.refreshToken,
          this.expiresInSecs,
          this.tokenCreationEpochInSecs,
          this.fisError);
    }
  }

}
