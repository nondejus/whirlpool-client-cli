package com.samourai.whirlpool.cli.api.protocol.rest;

import com.samourai.whirlpool.cli.api.protocol.beans.ApiUtxo;
import com.samourai.whirlpool.client.wallet.beans.MixOrchestratorState;
import com.samourai.whirlpool.client.wallet.beans.WhirlpoolWalletState;
import java.util.Collection;
import java.util.stream.Collectors;

public class ApiWalletStateResponse {
  private boolean started;

  private int nbMixing;
  private int nbQueued;
  private Collection<ApiUtxo> threads;

  public ApiWalletStateResponse(WhirlpoolWalletState whirlpoolWalletState) {
    this.started = whirlpoolWalletState.isStarted();

    MixOrchestratorState mixState = whirlpoolWalletState.getMixState();
    this.nbMixing = mixState.getNbMixing();
    this.nbQueued = mixState.getNbQueued();
    this.threads =
        mixState
            .getUtxosMixing()
            .stream()
            .map(whirlpoolUtxo -> new ApiUtxo(whirlpoolUtxo))
            .collect(Collectors.toList());
  }

  public boolean isStarted() {
    return started;
  }

  public int getNbMixing() {
    return nbMixing;
  }

  public int getNbQueued() {
    return nbQueued;
  }

  public Collection<ApiUtxo> getThreads() {
    return threads;
  }
}
