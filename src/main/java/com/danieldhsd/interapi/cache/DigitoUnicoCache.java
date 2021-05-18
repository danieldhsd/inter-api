package com.danieldhsd.interapi.cache;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.danieldhsd.interapi.model.DigitoUnico;

public class DigitoUnicoCache {

	public Map<String, CacheValue> cacheMap;
	
	private final static Integer MAX_SIZE_CACHE = 10;
	
	public DigitoUnicoCache() {
		this.clear();
	}
	
	public void clear() {
		this.cacheMap = new HashMap<String, CacheValue>();
	}
	
	public boolean containsKey(String key) {
		return this.cacheMap.containsKey(key);
	}
	
	public Optional<DigitoUnico> get(String key) {
		return Optional.ofNullable(this.cacheMap.get(key)).map(CacheValue::getValue);
	}
	
	public void put(String key, DigitoUnico value) {
		if(isFull())
			removeOlderInserted();
		
		this.cacheMap.put(key, this.createCacheValue(value));
	}
	
	public boolean isFull() {
		return this.cacheMap.size() == MAX_SIZE_CACHE;
	}
	
	public void removeOlderInserted() {
		this.cacheMap.remove(getOlderInserted());
	}
	
	private String getOlderInserted() {
		if(this.cacheMap == null || this.cacheMap.isEmpty())
			return "";
		
		LocalDateTime older = LocalDateTime.now();
		String olderKey = "";
		
		for(Map.Entry<String, CacheValue> entry : this.cacheMap.entrySet()) {
			if(entry.getValue().getCreatedAt().isBefore(older)) {
				older = entry.getValue().getCreatedAt();
				olderKey = entry.getKey();
			}
		}
		
		return olderKey;
	}
	
	private CacheValue createCacheValue(DigitoUnico digitoUnico) {
		LocalDateTime now = LocalDateTime.now();
		
		return new CacheValue() {
			@Override
			public DigitoUnico getValue() {
				return digitoUnico;
			}

			@Override
			public LocalDateTime getCreatedAt() {
				return now;
			}
		};
	}
	
	protected interface CacheValue {
		DigitoUnico getValue();
		
		LocalDateTime getCreatedAt();
	}
}
