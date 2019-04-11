package com.samourai.whirlpool.cli.api.controllers.pools;

import com.samourai.whirlpool.cli.api.controllers.AbstractRestController;
import com.samourai.whirlpool.cli.api.protocol.CliApiEndpoint;
import com.samourai.whirlpool.cli.api.protocol.rest.ApiPoolsResponse;
import com.samourai.whirlpool.cli.services.CliWalletService;
import com.samourai.whirlpool.client.tx0.Tx0Service;
import com.samourai.whirlpool.client.wallet.WhirlpoolWallet;
import com.samourai.whirlpool.client.whirlpool.beans.Pool;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PoolsController extends AbstractRestController {
  @Autowired private CliWalletService cliWalletService;
  @Autowired private Tx0Service tx0Service;

  @RequestMapping(value = CliApiEndpoint.REST_POOLS, method = RequestMethod.GET)
  public ApiPoolsResponse pools(@RequestHeader HttpHeaders headers) throws Exception {
    checkHeaders(headers);
    WhirlpoolWallet whirlpoolWallet = cliWalletService.getSessionWallet();
    Collection<Pool> poolsByPreference = whirlpoolWallet.getPoolsByPreference(true); // clear cache
    Collection<Pool> poolsAvailable = whirlpoolWallet.getPoolsAvailable(false);
    int feeSatPerByte = whirlpoolWallet.getFeeSatPerByte();
    return new ApiPoolsResponse(poolsByPreference, poolsAvailable, feeSatPerByte, tx0Service);
  }
}
