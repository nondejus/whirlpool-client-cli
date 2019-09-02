package com.samourai.whirlpool.cli.run;

import com.samourai.whirlpool.cli.api.protocol.beans.ApiCliConfig;
import com.samourai.whirlpool.cli.config.CliConfig;
import com.samourai.whirlpool.cli.services.CliConfigService;
import java.lang.invoke.MethodHandles;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RunUpgradeCli {
  private Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  private static final int CLI_V1 = 1;
  private static final int CLI_V2 = 2;

  private CliConfig cliConfig;
  private CliConfigService cliConfigService;

  public RunUpgradeCli(CliConfig cliConfig, CliConfigService cliConfigService) {
    this.cliConfig = cliConfig;
    this.cliConfigService = cliConfigService;
  }

  public void run(int lastVersion) throws Exception {
    // run upgrades
    if (lastVersion < CLI_V1) {
      upgradeV1();
    }

    if (lastVersion < CLI_V2) {
      upgradeV2();
    }
  }

  public void upgradeV1() throws Exception {
    log.info(" - Upgrading to: V1");
  }

  public void upgradeV2() throws Exception {
    log.info(" - Upgrading to: V2");

    // set mix.clients=3
    Properties props = cliConfigService.loadProperties();
    props.put(ApiCliConfig.KEY_MIX_CLIENTS, Integer.toString(3));
    cliConfigService.saveProperties(props);
  }
}
