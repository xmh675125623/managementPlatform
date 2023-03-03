import echarts from 'echarts'
function getLabelData () {
  const labelData = []
  for (let i = 0; i < 200; ++i) {
    labelData.push({
      value: 2,
      name: i,
      itemStyle: {
        normal: {
          color: 'rgba(0,209,228,0)'
        }
      }
    })
  }
  for (let i = 0; i < labelData.length; ++i) {
    if (labelData[i].name < 70 && i % 2 === 1) {
      labelData[i].value = 1
      labelData[i].itemStyle = {
        normal: {
          color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [
            {
              offset: 0,
              color: '#6dfbff'
            },
            {
              offset: 1,
              color: '#02aeff'
            }
          ])
        }
      }
    } else if (i % 2 === 1) {
      labelData[i].value = 1
      labelData[i].itemStyle = {
        normal: {
          color: '#464451'
        }
      }
    }
  }
  return labelData
}
function Pie () {
  const dataArr = []
  // for (let i = 0; i < 100; i++) {
  //   if (i % 10 === 0) {
  //     dataArr.push({
  //       name: (i + 1).toString(),
  //       value: 30,
  //       itemStyle: {
  //         normal: {
  //           color: 'rgba(0,255,255,1)',
  //           borderWidth: 0,
  //           borderColor: 'rgba(0,0,0,0)'
  //         }
  //       }
  //     })
  //   } else {
  //     dataArr.push({
  //       name: (i + 1).toString(),
  //       value: 100,
  //       itemStyle: {
  //         normal: {
  //           color: 'rgba(0,0,0,0)',
  //           borderWidth: 0,
  //           borderColor: 'rgba(0,0,0,0)'
  //         }
  //       }
  //     })
  //   }
  // }
  return dataArr
}

function Pie1 () {
  const dataArr = []
  // for (let i = 0; i < 100; i++) {
  //   if (i % 5 === 0) {
  //     dataArr.push({
  //       name: (i + 1).toString(),
  //       value: 20,
  //       itemStyle: {
  //         normal: {
  //           color: 'rgba(0,255,255,1)',
  //           borderWidth: 0,
  //           borderColor: 'rgba(0,0,0,0)'
  //         }
  //       }
  //     })
  //   } else {
  //     dataArr.push({
  //       name: (i + 1).toString(),
  //       value: 100,
  //       itemStyle: {
  //         normal: {
  //           color: 'rgba(0,0,0,0)',
  //           borderWidth: 0,
  //           borderColor: 'rgba(0,0,0,0)'
  //         }
  //       }
  //     })
  //   }
  // }
  return dataArr
}

function Pie2 () {
  const dataArr = []
  // for (let i = 0; i < 100; i++) {
  //   if (i % 5 === 0) {
  //     dataArr.push({
  //       name: (i + 1).toString(),
  //       value: 20,
  //       itemStyle: {
  //         normal: {
  //           color: 'rgba(0,255,255,.3)',
  //           borderWidth: 0,
  //           borderColor: 'rgba(0,0,0,0)'
  //         }
  //       }
  //     })
  //   } else {
  //     dataArr.push({
  //       name: (i + 1).toString(),
  //       value: 100,
  //       itemStyle: {
  //         normal: {
  //           color: 'rgba(0,0,0,0)',
  //           borderWidth: 0,
  //           borderColor: 'rgba(0,0,0,0)'
  //         }
  //       }
  //     })
  //   }
  // }
  return dataArr
}

function Pie3 () {
  const dataArr = []
  // for (let i = 0; i < 100; i++) {
  //   if (i % 10 === 0) {
  //     dataArr.push({
  //       name: (i + 1).toString(),
  //       value: 30,
  //       itemStyle: {
  //         normal: {
  //           color: 'rgba(0,255,255,.5)',
  //           borderWidth: 0,
  //           borderColor: 'rgba(0,0,0,0)'
  //         }
  //       }
  //     })
  //   } else {
  //     dataArr.push({
  //       name: (i + 1).toString(),
  //       value: 100,
  //       itemStyle: {
  //         normal: {
  //           color: 'rgba(0,0,0,0)',
  //           borderWidth: 0,
  //           borderColor: 'rgba(0,0,0,0)'
  //         }
  //       }
  //     })
  //   }
  // }
  return dataArr
}
export const deviceCountOption = {
  backgroundColor: 'rgba(0, 0, 0, 0)',
  title: [
    {
      text: '0',
      x: '49%',
      y: '40%',
      textAlign: 'center',
      textStyle: {
        fontSize: '40',
        fontWeight: '100',
        color: '#79ffff',
        textAlign: 'center'
      }
    }
  ],
  polar: {
    radius: ['67.5%', '73%'],
    center: ['50%', '50%']
  },
  angleAxis: {
    max: 100,
    show: false,
    startAngle: 0
  },
  radiusAxis: {
    type: 'category',
    show: true,
    axisLabel: {
      show: false
    },
    axisLine: {
      show: false
    },
    axisTick: {
      show: false
    }
  },
  series: [
    {
      name: '',
      type: 'bar',
      roundCap: true,
      barWidth: 60,
      showBackground: true,
      backgroundStyle: {
        color: '#464451'
      },
      data: [85],
      coordinateSystem: 'polar',
      itemStyle: {
        normal: {
          color: new echarts.graphic.LinearGradient(1, 0, 0, 0, [
            {
              offset: 0,
              color: '#0ff'
            },
            {
              offset: 1,
              color: '#02aeff'
            }
          ])
        }
      }
    },
    {
      hoverAnimation: false,
      type: 'pie',
      z: 2,
      data: getLabelData(),
      radius: ['75%', '86%'],
      zlevel: -2,
      itemStyle: {
        normal: {
          borderWidth: 4
        }
      },
      label: {
        normal: {
          position: 'inside',
          show: false
        }
      }
    },
    {
      type: 'pie',
      radius: ['62%', '63.5%'],
      center: ['50%', '50%'],
      data: [
        {
          hoverOffset: 1,
          value: 100,
          name: '',
          itemStyle: {
            color: '#ff6189'
          },
          label: {
            show: false
          },
          labelLine: {
            normal: {
              smooth: true,
              lineStyle: {
                width: 0
              }
            }
          },
          hoverAnimation: false
        },
        {
          label: {
            show: false
          },
          labelLine: {
            normal: {
              smooth: true,
              lineStyle: {
                width: 0
              }
            }
          },
          value: 100 - 75,
          hoverAnimation: false,
          itemStyle: {
            color: '#3c3a48'
          }
        }
      ]
    },

    {
      type: 'pie',
      zlevel: 0,
      silent: true,
      radius: ['97%', '95.5%'],
      z: 1,
      label: {
        normal: {
          show: false
        }
      },
      labelLine: {
        normal: {
          show: false
        }
      },
      data: Pie()
    },
    {
      type: 'pie',
      zlevel: 0,
      silent: true,
      startAngle: -150,
      radius: ['95%', '93.5%'],
      z: 1,
      label: {
        normal: {
          show: false
        }
      },
      labelLine: {
        normal: {
          show: false
        }
      },
      data: Pie3()
    },
    {
      type: 'pie',
      zlevel: 0,
      silent: true,
      startAngle: -140,
      radius: ['98%', '96.5%'],
      z: 1,
      label: {
        normal: {
          show: false
        }
      },
      labelLine: {
        normal: {
          show: false
        }
      },
      data: Pie()
    },
    {
      type: 'pie',
      zlevel: 0,
      silent: true,
      radius: ['91%', '90%'],
      z: 1,
      label: {
        normal: {
          show: false
        }
      },
      labelLine: {
        normal: {
          show: false
        }
      },
      data: Pie1()
    },
    {
      type: 'pie',
      zlevel: 0,
      silent: true,
      startAngle: -140,
      radius: ['91%', '90%'],
      z: 1,
      label: {
        normal: {
          show: false
        }
      },
      labelLine: {
        normal: {
          show: false
        }
      },
      data: Pie2()
    },
    {
      type: 'pie',
      zlevel: 0,
      silent: true,
      startAngle: -147.5,
      radius: ['91%', '90%'],
      z: 1,
      label: {
        normal: {
          show: false
        }
      },
      labelLine: {
        normal: {
          show: false
        }
      },
      data: Pie2()
    }
  ]
}
