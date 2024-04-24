package yalong.site;

import lombok.extern.slf4j.Slf4j;
import yalong.site.bo.ChampionBO;
import yalong.site.bo.LeagueClientBO;
import yalong.site.cache.AppCache;
import yalong.site.cache.FrameInnerCache;
import yalong.site.cache.GameDataCache;
import yalong.site.frame.bo.ItemBO;
import yalong.site.frame.utils.FrameMsgUtil;
import yalong.site.http.RequestLcuUtil;
import yalong.site.services.lcu.LeagueClientService;
import yalong.site.services.lcu.LinkLeagueClientApi;
import yalong.site.services.word.LoadGarbageWord;
import yalong.site.utils.ProcessUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author yalong
 */
@Slf4j
public class ClientStarter {
	private LinkLeagueClientApi api;
	private LeagueClientService leagueClientService;

	private ClientStarter() {
	}

	public static void start() throws Exception {
		ClientStarter clientStarter = new ClientStarter();
		clientStarter.initLcu();
		clientStarter.cacheData();
		clientStarter.loadFrameData();
		clientStarter.listenGameStatus();
	}

	private void initLcu() throws Exception {
		LeagueClientBO leagueClientBO = ProcessUtil.getClientProcess();
		assert !leagueClientBO.equals(new LeagueClientBO());
		RequestLcuUtil requestUtil = new RequestLcuUtil(leagueClientBO);
		api = new LinkLeagueClientApi(requestUtil);
		leagueClientService = new LeagueClientService(api);
	}

	private void cacheData() {
		// 加载垃圾话
		LoadGarbageWord loadGarbageWord = new LoadGarbageWord();
		AppCache.communicateWords = loadGarbageWord.loadWord();
		log.info("垃圾话加载完成");

		// 缓存登录人的信息
		try {
			GameDataCache.me = api.getCurrentSummoner();
		} catch (Exception e) {
			log.error("获取登录人信息错误");
		}

		//缓存所有英雄
		try {
			AppCache.allChampion = api.getAllChampion();
		} catch (Exception e) {
			log.error("获取所有英雄错误");
		}
	}

	private void loadFrameData(){
		// 所有英雄添加到面板下拉框
		int itemCount = FrameInnerCache.pickBox.getItemCount();
		if (itemCount == 1) {
			for (ChampionBO bo : AppCache.allChampion) {
				FrameInnerCache.pickBox.addItem(new ItemBO(String.valueOf(bo.getId()), bo.getName()));
				FrameInnerCache.banBox.addItem(new ItemBO(String.valueOf(bo.getId()), bo.getName()));
			}
			log.info("添加到面板下拉框完成");
		}
	}


	@SuppressWarnings("InfiniteLoopStatement")
	private void listenGameStatus() throws InterruptedException, IOException {
		while (true) {
			TimeUnit.MILLISECONDS.sleep(500);
			leagueClientService.switchGameStatus();
		}
	}

}