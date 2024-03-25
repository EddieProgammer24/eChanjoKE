/*
Copyright (C) 2024  Wrichik Basu (basulabs.developer@gmail.com)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as published
by the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/
package com.example.eChanjoKE.backend;

import android.net.Uri;
import android.os.Bundle;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.eChanjoKE.backend.ConstantsAndStatics;


@Entity(tableName = "alarm_entity", indices = {@Index(value = {"alarmHour",
	"alarmMinutes"}, unique = true)}
	/*primaryKeys = {"alarmHour", "alarmMinutes"}*/)
public class AlarmEntity {


	@PrimaryKey(autoGenerate = true)
	@ColumnInfo
	public int alarmID;

	/**
	 * The hour of the alarm in 24-hour format.
	 */
	public int alarmHour;

	/**
	 * The minutes of the alarm.
	 */
	public int alarmMinutes;

	/**
	 * A boolean value representing whether the alarm is ON or OFF.
	 */
	public boolean isAlarmOn;

	/**
	 * The date on which the alarm should ring.
	 */
	public int alarmDay;

	/**
	 * The month of the alarm.
	 */
	public int alarmMonth;

	/**
	 * The year of the alarm.
	 */
	public int alarmYear;

	/**
	 * A boolean value representing whether the snooze is ON or OFF.
	 */
	public boolean isSnoozeOn;

	/**
	 * The snooze time in minutes, i.e. after how many minutes the alarm should repeat
	 * itself after being cancelled initially.
	 */
	public int snoozeTimeInMinutes;

	/**
	 * The snooze frequency, i.e. how many times the alarm should repeat itself after
	 * being cancelled initially.
	 */
	public int snoozeFrequency;

	/**
	 * The alarm volume. Can be any value between 0 and what
	 * {@link android.media.AudioManager#getStreamMaxVolume(int)} returns for the
	 * {@link android.media.AudioManager#STREAM_ALARM}.
	 */
	public int alarmVolume;

	/**
	 * A boolean value indicating whether the alarm is set to repeat on any of the
	 * days in
	 * the week.
	 */
	public boolean isRepeatOn;


	public int alarmType;

	/**
	 * The {@link Uri} to the alarm tone.
	 */
	public Uri alarmTone;

	/**
	 * Indicates whether the user has specifically chosen a date for the alarm.
	 */
	public boolean hasUserChosenDate;

	/**
	 * A personalised message for the alarm.
	 */
	public String alarmMessage;

	public AlarmEntity(int alarmHour, int alarmMinutes, boolean isAlarmOn,
		boolean isSnoozeOn,
		int snoozeTimeInMinutes, int snoozeFrequency, int alarmVolume,
		boolean isRepeatOn,
		int alarmType, int alarmDay, int alarmMonth, int alarmYear, Uri alarmTone,
		String alarmMessage,
		boolean hasUserChosenDate) {

		this.alarmHour = alarmHour;
		this.alarmMinutes = alarmMinutes;
		this.isAlarmOn = isAlarmOn;
		this.isSnoozeOn = isSnoozeOn;
		this.snoozeTimeInMinutes = snoozeTimeInMinutes;
		this.snoozeFrequency = snoozeFrequency;
		this.alarmVolume = alarmVolume;
		this.isRepeatOn = isRepeatOn;
		this.alarmType = alarmType;
		this.alarmDay = alarmDay;
		this.alarmMonth = alarmMonth;
		this.alarmYear = alarmYear;
		this.alarmTone = alarmTone;
		this.alarmMessage = alarmMessage;
		this.hasUserChosenDate = hasUserChosenDate;
	}

	/**
	 * Get all the alarm details in a single bundle.
	 *
	 * @return All the information in the alarm entitiy in a single Bundle.
	 */
	@Ignore
	public Bundle getAlarmDetailsInABundle() {
		Bundle data = new Bundle();
		data.putInt(ConstantsAndStatics.BUNDLE_KEY_ALARM_ID, alarmID);
		data.putBoolean(ConstantsAndStatics.BUNDLE_KEY_IS_ALARM_ON, isAlarmOn);
		data.putInt(ConstantsAndStatics.BUNDLE_KEY_ALARM_HOUR, alarmHour);
		data.putInt(ConstantsAndStatics.BUNDLE_KEY_ALARM_MINUTE, alarmMinutes);
		data.putInt(ConstantsAndStatics.BUNDLE_KEY_ALARM_DAY, alarmDay);
		data.putInt(ConstantsAndStatics.BUNDLE_KEY_ALARM_MONTH, alarmMonth);
		data.putInt(ConstantsAndStatics.BUNDLE_KEY_ALARM_YEAR, alarmYear);
		data.putInt(ConstantsAndStatics.BUNDLE_KEY_ALARM_TYPE, alarmType);
		data.putBoolean(ConstantsAndStatics.BUNDLE_KEY_IS_SNOOZE_ON, isSnoozeOn);
		data.putBoolean(ConstantsAndStatics.BUNDLE_KEY_IS_REPEAT_ON, isRepeatOn);
		data.putInt(ConstantsAndStatics.BUNDLE_KEY_ALARM_VOLUME, alarmVolume);
		data.putInt(ConstantsAndStatics.BUNDLE_KEY_SNOOZE_TIME_IN_MINS,
			snoozeTimeInMinutes);
		data.putInt(ConstantsAndStatics.BUNDLE_KEY_SNOOZE_FREQUENCY, snoozeFrequency);
		data.putParcelable(ConstantsAndStatics.BUNDLE_KEY_ALARM_TONE_URI, alarmTone);
		data.putString(ConstantsAndStatics.BUNDLE_KEY_ALARM_MESSAGE, alarmMessage);
		data.putBoolean(ConstantsAndStatics.BUNDLE_KEY_HAS_USER_CHOSEN_DATE,
			hasUserChosenDate);
		return data;
	}

}
