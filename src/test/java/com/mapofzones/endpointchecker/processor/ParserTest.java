package com.mapofzones.endpointchecker.processor;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {
    public BufferedReader reader;

    @BeforeEach
    void setUp() {
        String responseBody = "" +
                "{\n" +
                "  \"jsonrpc\": \"2.0\",\n" +
                "  \"id\": -1,\n" +
                "  \"result\": {\n" +
                "    \"node_info\": {\n" +
                "      \"protocol_version\": {\n" +
                "        \"p2p\": \"8\",\n" +
                "        \"block\": \"11\",\n" +
                "        \"app\": \"0\"\n" +
                "      },\n" +
                "      \"id\": \"ba9643bba9c07656a4b965029c24251a96c2ed74\",\n" +
                "      \"listen_addr\": \"tcp://0.0.0.0:26656\",\n" +
                "      \"network\": \"cosmoshub-4\",\n" +
                "      \"version\": \"v0.34.8\",\n" +
                "      \"channels\": \"40202122233038606100\",\n" +
                "      \"moniker\": \"zoomerlabs\",\n" +
                "      \"other\": {\n" +
                "        \"tx_index\": \"on\",\n" +
                "        \"rpc_address\": \"tcp://0.0.0.0:26657\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"sync_info\": {\n" +
                "      \"latest_block_hash\": \"772C1F87DE241370FFA5B1235FC39B250F4D0CD1C026304C1C81B8D21C935AF4\",\n" +
                "      \"latest_app_hash\": \"0F046CED2AAFFA7BEDEADADDA0349CDA184720F988CB45AA57D7918DA79C5793\",\n" +
                "      \"latest_block_height\": \"6168961\",\n" +
                "      \"latest_block_time\": \"2021-05-10T16:39:43.33332566Z\",\n" +
                "      \"earliest_block_hash\": \"1455A0C15AC49BB506992EC85A3CD4D32367E53A087689815E01A524231C3ADF\",\n" +
                "      \"earliest_app_hash\": \"E3B0C44298FC1C149AFBF4C8996FB92427AE41E4649B934CA495991B7852B855\",\n" +
                "      \"earliest_block_height\": \"5200791\",\n" +
                "      \"earliest_block_time\": \"2019-12-11T16:11:34Z\",\n" +
                "      \"catching_up\": false\n" +
                "    },\n" +
                "    \"validator_info\": {\n" +
                "      \"address\": \"70A89F98D50436AC1460521792578BA2C54D97CD\",\n" +
                "      \"pub_key\": {\n" +
                "        \"type\": \"tendermint/PubKeyEd25519\",\n" +
                "        \"value\": \"p+1FKcPq18ceiwIWje6FFXVo8R7mXyN39uPUd7Gw9Cg=\"\n" +
                "      },\n" +
                "      \"voting_power\": \"0\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        Reader inputString = new StringReader(responseBody);
        reader = new BufferedReader(inputString);
    }

    @AfterEach
    void tearDown() {
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void parseChainId() {
        String expected = "cosmoshub-4";
        String actual = null;
        try {
            actual = Parser.parseChainId(reader, "network");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(expected, actual);
    }

}